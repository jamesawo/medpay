import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:medpay/data/enums/app_enum.dart';
import 'package:medpay/data/models/transaction_model.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/app_routes.dart';
import 'package:medpay/utils/helper_utils.dart';
import 'package:medpay/utils/style_utils.dart';
import 'package:medpay/widgets/app_top_title.dart';
import 'package:medpay/widgets/empty_state_widget.dart';
import 'package:medpay/widgets/search_input_with_button.dart';
import 'package:medpay/widgets/text_small.dart';
import 'package:medpay/widgets/transaction_not_successful.dart';
import 'package:medpay/widgets/transaction_successful.dart';

class PaymentVerificationScreen extends StatefulWidget {
  const PaymentVerificationScreen({Key? key}) : super(key: key);

  @override
  State<PaymentVerificationScreen> createState() => _PaymentVerificationScreenState();
}

class _PaymentVerificationScreenState extends State<PaymentVerificationScreen> {
  late bool showConfirmation;
  late SuccessOrErrorEnum status;
  late String message;
  late TransactionModel transaction;
  late TextEditingController controller;

  @override
  void initState() {
    super.initState();
    showConfirmation = false;
    transaction = TransactionModel();
    controller = TextEditingController();
  }

  @override
  Widget build(BuildContext context) {
    return AnnotatedRegion<SystemUiOverlayStyle>(
      value: SystemUiOverlayStyle.dark,
      child: Scaffold(
        resizeToAvoidBottomInset: true,
        backgroundColor: ThemeColorStyle.appWhite,
        body: SafeArea(
          child: SingleChildScrollView(
            scrollDirection: Axis.vertical,
            reverse: false,
            child: Column(
              children: [
                const AppTopTitle(title: 'Verify Payment', showMoreVert: false),
                Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  crossAxisAlignment: CrossAxisAlignment.center,
                  mainAxisSize: MainAxisSize.max,
                  children: [
                    Container(
                      margin: EdgeInsets.only(
                        left: AppDimensions.width30,
                        right: AppDimensions.width30,
                        bottom: AppDimensions.height10,
                        top: AppDimensions.height20,
                      ),
                      child: Column(
                        children: [
                          Align(
                            child: TextSmall(
                              text: "Enter Transaction Ref.",
                              align: TextAlign.left,
                              size: AppDimensions.fontSize14,
                            ),
                            alignment: AlignmentDirectional.topStart,
                          ),
                          SizedBox(height: AppDimensions.height5),
                          SearchInputWithButton(
                            textController: controller,
                            searchText: controller.text,
                            hintText: 'Enter your ref number',
                            action: () {
                              onVerifyTransaction(controller.text);
                            },
                          )
                        ],
                      ),
                    ),
                    SizedBox(height: AppDimensions.height20),
                    showConfirmation
                        ? Column(
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: [
                              status == SuccessOrErrorEnum.success
                                  ? TransactionSuccessful(transaction: transaction)
                                  : TransactionNotSuccessful(transaction: transaction),
                              SizedBox(height: AppDimensions.height30),
                              onGetActionButton(),
                            ],
                          )
                        : SizedBox(
                            height: AppDimensions.height240,
                            child: Column(
                              mainAxisAlignment: MainAxisAlignment.center,
                              children: const [
                                EmptyStateWidget(message: 'Search For A Transaction.'),
                              ],
                            ),
                          ),
                  ],
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }

  ElevatedButton onGetActionButton() {
    return ElevatedButton(
      style: ThemeInputStyle.primaryButtonStyle,
      child: TextSmall(
        text: 'Go Back Home',
        size: AppDimensions.fontSize16,
        color: ThemeColorStyle.appWhite,
        weight: FontWeight.w600,
      ),
      onPressed: () {
        HelperUtils.navigateTo(AppRoutes.homeDashboardScreen);
      },
    );
  }

  void onVerifyTransaction(String transReference) async {
    // PaymentController controller = Get.find<PaymentController>();
    // var payload = await controller.onVerifyTransaction();
    toggleConfirmation();
  }

  void toggleConfirmation() {
    setState(() {
      showConfirmation = !showConfirmation;
    });
  }
}
