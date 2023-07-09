import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:get/get.dart';
import 'package:medpay/controllers/hospital_controller.dart';
import 'package:medpay/controllers/payment_controller.dart';
import 'package:medpay/data/models/bill_model.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/style_utils.dart';
import 'package:medpay/widgets/app_button_loading.dart';
import 'package:medpay/widgets/app_top_title.dart';
import 'package:medpay/widgets/bill_item_table.dart';
import 'package:medpay/widgets/text_large.dart';
import 'package:medpay/widgets/text_medium.dart';
import 'package:medpay/widgets/text_small.dart';

class PaymentBillDetailScreen extends StatefulWidget {
  final HospitalBill? bill;

  const PaymentBillDetailScreen({
    required this.bill,
    Key? key,
  }) : super(key: key);

  @override
  State<PaymentBillDetailScreen> createState() => _PaymentBillDetailScreenState();
}

class _PaymentBillDetailScreenState extends State<PaymentBillDetailScreen> {
  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return GetBuilder<HospitalController>(builder: (controller) {
      return AnnotatedRegion<SystemUiOverlayStyle>(
        value: SystemUiOverlayStyle.dark,
        child: Scaffold(
          resizeToAvoidBottomInset: true,
          backgroundColor: ThemeColorStyle.appWhite,
          body: SafeArea(
            child: CustomScrollView(
              scrollDirection: Axis.vertical,
              reverse: false,
              slivers: [
                const SliverAppBar(
                  backgroundColor: ThemeColorStyle.appWhite,
                  automaticallyImplyLeading: false,
                  title: AppTopTitle(title: 'Make Payment'),
                  titleSpacing: 0,
                  pinned: true,
                ),
                SliverToBoxAdapter(
                  child: Column(
                    children: [
                      Divider(color: ThemeColorStyle.appGray20, thickness: AppDimensions.height15),
                      // hospital detail
                      getHospitalDetail(controller),
                      getSpaceAndSeparator(),
                      // patient detail
                      getBillPatientDetail(controller),
                      getSpaceAndSeparator(),
                      // bill items table
                      BillItemTable(items: widget.bill?.items!),
                      // bill amount detail
                      getSpaceAndSeparator(),
                      getBillAmountDetail(),
                      SizedBox(height: AppDimensions.height30),
                    ],
                  ),
                ),
              ],
            ),
          ),
          bottomNavigationBar: getButtonActionButton(),
        ),
      );
    });
  }

  Widget getHospitalDetail(HospitalController controller) {
    var hospital = controller.selectedHospital;
    return Container(
      margin: EdgeInsets.only(
        left: AppDimensions.width15,
        top: AppDimensions.height10,
        bottom: AppDimensions.height10,
      ),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.start,
        children: [
          Expanded(
            flex: 1,
            child: Container(
              height: AppDimensions.height50 + 10,
              decoration: BoxDecoration(
                image: DecorationImage(
                  image: NetworkImage(hospital?.logoUrl ?? ''),
                  fit: BoxFit.contain,
                ),
                borderRadius: BorderRadius.circular(50),
              ),
            ),
          ),
          Expanded(
            flex: 3,
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                TextSmall(text: 'Hospital info', size: AppDimensions.fontSize12),
                TextMedium(text: '${hospital?.title}', size: AppDimensions.fontSize18),
                SizedBox(height: AppDimensions.height2),
                TextSmall(text: '${hospital?.address}', size: AppDimensions.fontSize10),
              ],
            ),
          ),
        ],
      ),
    );
  }

  Widget getBillPatientDetail(HospitalController controller) {
    return Container(
      margin: EdgeInsets.symmetric(
        horizontal: AppDimensions.width25,
      ),
      child: Column(
        children: [
          SizedBox(height: AppDimensions.height5),
          Row(
            children: [
              TextMedium(
                text: 'Patient Name:',
                size: AppDimensions.fontSize15,
              ),
              Expanded(
                child: TextMedium(
                  align: TextAlign.end,
                  text: widget.bill?.patient?.fullName ?? '',
                  size: AppDimensions.fontSize15,
                ),
              )
            ],
          ),
          SizedBox(height: AppDimensions.height5),
          Row(
            children: [
              TextMedium(
                text: 'Patient Number:',
                size: AppDimensions.fontSize15,
              ),
              Expanded(
                child: TextMedium(
                  align: TextAlign.end,
                  text: widget.bill?.patient?.patientNumber ?? '',
                  size: AppDimensions.fontSize15,
                ),
              )
            ],
          ),
          SizedBox(height: AppDimensions.height5),
          SizedBox(height: AppDimensions.height5),
          const Divider(
            color: ThemeColorStyle.appGray20,
            thickness: 1,
          ),
          Row(
            children: [
              TextMedium(
                text: 'Invoice Number:',
                size: AppDimensions.fontSize15,
              ),
              Expanded(
                child: TextMedium(
                  align: TextAlign.end,
                  text: widget.bill?.billNumber! ?? '',
                  size: AppDimensions.fontSize15,
                ),
              )
            ],
          ),
          SizedBox(height: AppDimensions.height5),
          Row(
            children: [
              TextMedium(
                text: 'Bill Date:',
                size: AppDimensions.fontSize15,
              ),
              Expanded(
                child: TextMedium(
                  align: TextAlign.end,
                  text: widget.bill?.date! ?? '',
                  size: AppDimensions.fontSize15,
                ),
              )
            ],
          ),
        ],
      ),
    );
  }

  Widget getSpaceAndSeparator() {
    return Column(
      children: [
        SizedBox(height: AppDimensions.height10),
        Divider(color: ThemeColorStyle.appGray20, thickness: AppDimensions.height15),
        SizedBox(height: AppDimensions.height10),
      ],
    );
  }

  Widget getBillAmountDetail() {
    return Container(
      margin: EdgeInsets.symmetric(
        horizontal: AppDimensions.width25,
      ),
      child: Column(
        children: [
          SizedBox(height: AppDimensions.height5),
          Row(
            children: [
              TextMedium(
                text: 'Discount',
                size: AppDimensions.fontSize15,
              ),
              Expanded(
                child: TextMedium(
                  align: TextAlign.end,
                  text: '${widget.bill?.total?.discountAmount}',
                  size: AppDimensions.fontSize15,
                ),
              )
            ],
          ),
          SizedBox(height: AppDimensions.height5),
          Row(
            children: [
              TextMedium(
                text: 'Gross Amount',
                size: AppDimensions.fontSize15,
              ),
              Expanded(
                child: TextMedium(
                  align: TextAlign.end,
                  text: '${widget.bill?.total?.grossAmount}',
                  size: AppDimensions.fontSize15,
                ),
              )
            ],
          ),
          SizedBox(height: AppDimensions.height8),
          Row(
            children: [
              TextLarge(
                text: 'Amount Payable',
                size: AppDimensions.fontSize18,
              ),
              Expanded(
                child: TextLarge(
                  align: TextAlign.end,
                  text: '${widget.bill?.total?.netAmount}',
                  size: AppDimensions.fontSize18,
                ),
              )
            ],
          ),
          SizedBox(height: AppDimensions.height8),
        ],
      ),
    );
  }

  Widget getButtonActionButton() {
    return GetBuilder<PaymentController>(
      builder: ((paymentController) {
        return Container(
          margin: EdgeInsets.symmetric(horizontal: AppDimensions.width25, vertical: AppDimensions.height20),
          padding: const EdgeInsets.all(0),
          child: paymentController.isMakingPayment == true
              ? const AppButtonLoading()
              : ElevatedButton(
                  style: ThemeInputStyle.primaryButtonStyle,
                  child: TextSmall(
                    text: 'Make Payment',
                    size: AppDimensions.fontSize15,
                    color: ThemeColorStyle.appWhite,
                    weight: FontWeight.w600,
                  ),
                  onPressed: () {
                    // processPayment();
                    getAlertConfirmDialog();
                  },
                ),
        );
      }),
    );
  }

  Future<void> processPayment() async {
    PaymentController paymentController = Get.find<PaymentController>();
    paymentController.handleBillPayment(widget.bill!, context);
  }

  Future<void> getAlertConfirmDialog() {
    return showDialog<void>(
      context: context,
      barrierDismissible: false, // user must tap button!
      builder: (BuildContext context) {
        return AlertDialog(
          title: const TextSmall(text: 'Process Payment ?'),
          content: SingleChildScrollView(
            child: ListBody(
              children: <Widget>[
                TextSmall(
                  text: 'Are you sure you want to continue?',
                  size: AppDimensions.fontSize14,
                ),
              ],
            ),
          ),
          actions: <Widget>[
            TextButton(
              child: const TextSmall(text: 'No, Cancel', color: ThemeColorStyle.appRed),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
            TextButton(
              child: const TextSmall(text: 'Yes, Continue', color: ThemeColorStyle.appBlue),
              onPressed: (){
                Navigator.of(context).pop();
                processPayment();
              },
            )
          ],
        );
      },
    );
  }
}
