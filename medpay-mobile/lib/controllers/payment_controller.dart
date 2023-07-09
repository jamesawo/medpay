import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:medpay/controllers/hospital_controller.dart';
import 'package:medpay/controllers/recent_transaction_controller.dart';
import 'package:medpay/data/constants/app_constants.dart';
import 'package:medpay/data/constants/exception_constants.dart';
import 'package:medpay/data/enums/app_enum.dart';
import 'package:medpay/data/models/bill_model.dart';
import 'package:medpay/data/models/hospital_model.dart';
import 'package:medpay/data/models/transaction_model.dart';
import 'package:medpay/data/models/user_model.dart';
import 'package:medpay/repositories/payment_repository.dart';
import 'package:medpay/utils/app_routes.dart';
import 'package:medpay/utils/app_shared_preference.dart';
import 'package:medpay/utils/helper_utils.dart';
import 'package:medpay/utils/logger_utils.dart';
import 'package:remita_flutter_inline/remita_flutter_inline.dart';

import 'auth_controller.dart';

class PaymentController extends GetxController {
  final PaymentRepository paymentRepository;

  late HospitalBill billModel;

  PaymentController({required this.paymentRepository});

  /// used to show a circular progress indicator
  ///
  /// when searching for bill details.
  bool _isSearching = false;

  bool get isSearching => _isSearching;

  set isSearching(bool value) {
    _isSearching = value;
    update();
  }

  /// Used to show a circular progress indicator
  ///
  /// When processing either service or bill payment.
  bool _isMakingPayment = false;

  bool get isMakingPayment => _isMakingPayment;

  set isMakingPayment(bool value) {
    _isMakingPayment = value;
    update();
  }

  /// Used to store result after searching for bill details
  List<HospitalBill> _searchResult = [];

  List<HospitalBill> get searchResult => _searchResult;

  /// Call endpoint to search for a bill details
  ///
  /// Makes connection to 3rd party api endpoint to get bill details
  Future<void> onSearchBillDetails(String billNumber) async {
    if (billNumber.isEmpty) {
      HelperUtils.showErrorSnackBar(
        title: ExceptionConstants.errorFriendlyTitle,
        message: ExceptionConstants.errorEmptyBillSearch,
      );
      return;
    }
    try {
      _searchResult = [];
      isSearching = true;
      String? hospitalId =
          Get.find<AppSharedPreference>().getValue(AppConstants.keyHospitalId);
      Response response =
          await paymentRepository.doSearchBillDetails(billNumber, hospitalId!);
      if (response.statusCode == 200) {
        if (response.body != null) {
          Map<String, dynamic> body = jsonDecode(jsonEncode(response.body));
          HospitalBill hospitalBill = HospitalBill.fromJson(body);
          _searchResult.add(hospitalBill);
          isSearching = false;
          return;
        }
        isSearching = false;
        return;
      } else {
        isSearching = false;
        HelperUtils.showErrorAndLog(response,
            '${(this).toString()} ${(this).onSearchBillDetails.toString()}');
        return;
      }
    } catch (e) {
      printError(info: e.toString());
      HelperUtils.showErrorSnackBar(
        title: ExceptionConstants.errorDefaultTitle,
        message: ExceptionConstants.errorDefaultMessage,
      );
      return;
    }
  }

  /// Search invoice number created from administrator side
  Future<void> onSearchInvoiceNumberDetails(String invoiceNumber) async {
    if (invoiceNumber.isEmpty) {
      HelperUtils.showErrorSnackBar(
        title: ExceptionConstants.errorFriendlyTitle,
        message: ExceptionConstants.errorEmptyBillSearch,
      );
      return;
    }
    try {
      _searchResult = [];
      isSearching = true;
      String? hospitalId =
          Get.find<AppSharedPreference>().getValue(AppConstants.keyHospitalId);
      Response response = await paymentRepository.doSearchInvoiceNumberDetails(
          invoiceNumber, hospitalId!);
      if (response.statusCode == 200) {
        if (response.body != null) {
          List<dynamic> listOfBills = jsonDecode(jsonEncode(response.body));
          if (listOfBills.isNotEmpty) {
            HospitalBill hospitalBill =
                HospitalBill.fromInvoiceJson(listOfBills[0]);
            _searchResult.add(hospitalBill);
            isSearching = false;
          }
          return;
        }
        isSearching = false;
        return;
      } else {
        isSearching = false;
        HelperUtils.showErrorAndLog(response,
            '${(this).toString()} ${(this).onSearchBillDetails.toString()}');
        return;
      }
    } catch (e) {
      printError(info: e.toString());
      HelperUtils.showErrorSnackBar(
        title: ExceptionConstants.errorDefaultTitle,
        message: ExceptionConstants.errorDefaultMessage,
      );
      return;
    }
  }

  /// Make payment for bill item
  ///
  /// used when hospital hasHospitalSoftware is false
  Future<void> handleBillPayment(
    HospitalBill bill,
    BuildContext context,
  ) async {
    HospitalController controller = Get.find<HospitalController>();
    var hospitalModel = await controller.getCurrentHospital();

    if (hospitalModel.collectionModelEnum == CollectionModeEnum.GATEWAY) {
      return makeGatewayBillPayment(bill, context);
    } else {
      return makeManualBillPayment(bill, context);
    }
  }

  Future<void> makeManualBillPayment(
    HospitalBill bill,
    BuildContext context,
  ) async {
    TransactionModel transaction = _prepTransactionForBillPayment(bill);
    return doTransactionPayment(transaction);
  }

  Future<void> makeGatewayBillPayment(
    HospitalBill bill,
    BuildContext context,
  ) async {
    try {
      isMakingPayment = true;
      // generate RRR
      TransactionModel transaction = _prepTransactionForBillPayment(bill);
      var response = await paymentRepository.doGenerateRrr(transaction);
      if (response.isOk && response.body != null) {
        var decoded = jsonDecode(jsonEncode(response.body));

        if (decoded.containsKey('RRR')) {
          var rrr = decoded['RRR'];
          _initializePayment(context, rrr, transaction);
        } else {
          isMakingPayment = false;
          HelperUtils.showErrorSnackBar(
            title: MessageConstants.paymentFailed,
            message: decoded['statusMessage'] ?? MessageConstants.paymentError,
          );
        }
      }
      isMakingPayment = false;
      return;
    } catch (e) {
      isMakingPayment = false;
      LoggerUtils.logError(
        error: e,
        caller: '${this}.toString() $handleBillPayment.toString()',
      );
      return;
    }
  }

  TransactionModel _prepTransactionForBillPayment(HospitalBill bill) {
    UserModel loggedInUser = Get.find<AuthController>().loggedInUser;
    HospitalModel selectedHospital =
        Get.find<HospitalController>().selectedHospital;
    TransactionPayerDetailModel payerDetail = TransactionPayerDetailModel(
      patientNumber: bill.patient?.patientNumber,
      fullName: bill.patient?.fullName,
      phoneNumber: bill.patient?.phoneNumber,
    );

    TransactionPaymentDetailModel paymentDetailModel =
        TransactionPaymentDetailModel(
            billNumber: bill.billNumber, services: bill.services);
    return TransactionModel(
        amount: num.tryParse(bill.total!.netAmount!),
        user: loggedInUser,
        hospital: selectedHospital,
        payerDetail: payerDetail,
        paymentDetail: paymentDetailModel);
  }

  Future<void> doTransactionPayment(TransactionModel transaction) async {
    try {
      isMakingPayment = true;

      Response response =
          await paymentRepository.doTransactionPayment(transaction);

      if (response.statusCode == 200) {
        Map<String, dynamic> body = jsonDecode(jsonEncode(response.body));
        TransactionModel transactionResponse = TransactionModel.fromJson(body);
        isMakingPayment = false;
        _onNavigateToPaymentStatusScreen(transactionResponse);
      } else {
        isMakingPayment = false;
        HelperUtils.showErrorAndLog(response,
            '${(this).toString()} ${(this).handleBillPayment.toString()}');
        return;
      }
    } catch (e) {
      isMakingPayment = false;
      HelperUtils.showErrorSnackBar(
        title: ExceptionConstants.errorDefaultTitle,
        message: ExceptionConstants.errorDefaultMessage,
      );
      LoggerUtils.logError(
          error: e, caller: '${this}.toString() $handleBillPayment.toString()');
      return;
    }
  }

  /// Make payment for service items
  ///
  /// used when hospital hasHospitalSoftware is false
  Future<void> handleServicePayment(
      {required String name,
      required String phone,
      required String amount}) async {
    try {
      isMakingPayment = true;
      TransactionModel transactionModel =
          _prepTransactionForServicePayment(name, phone, amount);
      Response response =
          await paymentRepository.doTransactionPayment(transactionModel);
      if (response.statusCode == 200) {
        Map<String, dynamic> body = jsonDecode(jsonEncode(response.body));
        TransactionModel transactionResponse = TransactionModel.fromJson(body);
        isSearching = false;
        _onNavigateToPaymentStatusScreen(transactionResponse);
      }
      isMakingPayment = false;
    } catch (e) {
      isMakingPayment = false;
      printError(info: e.toString());
      HelperUtils.showErrorSnackBar(
        title: ExceptionConstants.errorDefaultTitle,
        message: ExceptionConstants.errorDefaultMessage,
      );
      return;
    }
  }

  /// prepare transaction payload for service payment
  TransactionModel _prepTransactionForServicePayment(
      String name, String phone, String amount) {
    UserModel loggedInUser = Get.find<AuthController>().loggedInUser;
    HospitalController hospitalController = Get.find<HospitalController>();
    HospitalModel selectedHospital = hospitalController.selectedHospital;
    TransactionPayerDetailModel payerDetail =
        TransactionPayerDetailModel(fullName: name, phoneNumber: phone);
    TransactionPaymentDetailModel paymentDetailModel =
        TransactionPaymentDetailModel(services: hospitalController.services);
    return TransactionModel(
      amount: num.tryParse(amount),
      user: loggedInUser,
      hospital: selectedHospital,
      payerDetail: payerDetail,
      paymentDetail: paymentDetailModel,
    );
  }

  /// navigate user to payment confirm screen after payment is processed
  void _onNavigateToPaymentStatusScreen(TransactionModel data) {
    Get.find<RecentTransactionController>().addTransaction(data);
    HelperUtils.popBeforeNavigateToWithArgs(
        AppRoutes.paymentStatusScreen, {'transaction': data});
  }

  Future<void> _initializePayment(
    BuildContext context,
    String rrr,
    TransactionModel transaction,
  ) async {
    PaymentRequest request = PaymentRequest(
      environment: RemitaEnvironment.demo,
      rrr: rrr,
      key: AppConstants.devKey,
    );

    RemitaPayment remita = RemitaInlinePayment(
      buildContext: context,
      paymentRequest: request,
      customizer: Customizer(),
    );

    PaymentResponse response = await remita.initiatePayment();
    if (response.code != null && response.code == '00') {
      // transaction successful
      // verify transaction status before providing value
      doTransactionPayment(transaction);
    } else {
      // transaction not successful.
      HelperUtils.showErrorSnackBar(
        title: ExceptionConstants.errorDefaultTitle,
        message: MessageConstants.paymentFailed,
      );
    }
  }

/*
  Future<void> onHandlePayment(UserModel user, BillModel bill, BuildContext context) async {
    isMakingPayment = true;
    var amount = bill.payableAmount;
    var rrr = await _generateRRR(user, amount.toString());
    if (rrr.isEmpty) {
      HelperUtils.showErrorSnackBar(message: 'Could not generate payment reference');
      isMakingPayment = false;
      return;
    }

    var request = PaymentRequest(key: PaymentGatewayConstants.remittaPubKey, rrr: rrr, environment: RemittaEnvironment.demo);
    var remitta = RemittaInlinePayment(buildContext: context, paymentRequest: request);
    PaymentResponse response = await remitta.initiatePayment();
    _onAfterPayment(response, bill);
    isMakingPayment = false;
  }

  Future<Response> onVerifyTransaction() {
    return Future.value(const Response());
  }

  void _onAfterPayment(PaymentResponse response, BillModel bill) {
    if (response.data != null && response.data?.paymentReference != null) {
      var data = response.data;
      billModel = bill;
      var sharedPref = Get.find<AppSharedPreference>();
      var transaction = Transaction(
        reference: data!.paymentReference!,
        processorId: data.processorId,
        transactionId: data.transactionId!,
        billNumber: bill.billNumber!,
        amount: '${data.amount}',
        user: sharedPref.getUserUuid(),
        hospital: sharedPref.getHospitalUuid(),
      );
      _onSaveTransaction(transaction);
    } else {
      //todo:: save failed transaction to transaction history
      _onNavigateToPaymentFailed(TransactionResponse(
          reference: '',
          billNumber: bill.billNumber!,
          date: '${bill.date}',
          time: '${bill.time}',
          amount: '${bill.payableAmount}'));
    }
  }

  void _onSaveTransaction(Transaction transaction) async {
    try {
      Response response = await paymentRepository.doSaveTransaction(transaction);
      if (response.statusCode == 200) {
        TransactionResponse data = TransactionResponse.fromJson(jsonDecode(jsonEncode(response.body['data'])));
        _onNavigateToPaymentSuccess(response.body['message'], data);
      }
      // could not save transaction
    } catch (e) {
      throw Exception(e.toString());
    }
  }

  void _onNavigateToPaymentFailed(TransactionResponse data) {
    var map = {"message": "Transaction Failed", "status": SuccessOrErrorEnum.error};
    Get.find<RecentTransactionController>().addTransaction(data);
    HelperUtils.popBeforeNavigateToWithArgs(AppRoutes.paymentConfirmScreen, map);
  }

  Future<String> _generateRRR(UserModel user, String amount) async {
    try {
      Response response = await paymentRepository.doGenerateRRR(user.uuid!, amount);
      if (response.statusCode == 200) {
        var decode = jsonDecode(jsonEncode(response.body));
        Map<String, dynamic> map = json.decode(decode);
        if (map.containsKey("statuscode") && map['statuscode'] == '025') {
          return map['RRR'];
        }
      }
      return Future.value("");
    } catch (e) {
      throw Exception(e.toString());
    }
  }
   */

}
