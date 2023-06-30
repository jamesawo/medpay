import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:get/get.dart';
import 'package:medpay/controllers/payment_controller.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/app_routes.dart';
import 'package:medpay/utils/helper_utils.dart';
import 'package:medpay/utils/style_utils.dart';
import 'package:medpay/widgets/app_button_loading.dart';
import 'package:medpay/widgets/app_top_title.dart';
import 'package:medpay/widgets/form_input_with_label.dart';
import 'package:medpay/widgets/text_small.dart';

class PaymentPayerDetailScreen extends StatefulWidget {
  const PaymentPayerDetailScreen({Key? key}) : super(key: key);

  @override
  State<PaymentPayerDetailScreen> createState() => _PaymentPayerDetailScreenState();
}

class _PaymentPayerDetailScreenState extends State<PaymentPayerDetailScreen> {
  final TextEditingController _nameController = TextEditingController();
  final TextEditingController _phoneController = TextEditingController();
  final TextEditingController _amountController = TextEditingController();
  final GlobalKey<FormState> _globalKey = GlobalKey();

  @override
  Widget build(BuildContext context) {
    return GetBuilder<PaymentController>(builder: (controller) {
      return AnnotatedRegion<SystemUiOverlayStyle>(
        value: SystemUiOverlayStyle.light,
        child: Scaffold(
          resizeToAvoidBottomInset: true,
          backgroundColor: Colors.white,
          body: SafeArea(
            child: SingleChildScrollView(
              scrollDirection: Axis.vertical,
              reverse: false,
              child: Column(
                children: [
                  SizedBox(height: AppDimensions.height10),
                  AppTopTitle(title: 'Make Payment', action: onNavigateToAddItemsScreen),
                  SizedBox(height: AppDimensions.height35),
                  Form(
                    key: _globalKey,
                    child: Padding(
                      padding: EdgeInsets.only(
                        left: AppDimensions.width50,
                        right: AppDimensions.width50,
                      ),
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          FormInputWithLabel(
                            controller: _nameController,
                            label: "Payer's Full Name",
                            inputHintText: 'Enter name here',
                            inputType: InputType.text,
                          ),
                          SizedBox(height: AppDimensions.height20),
                          FormInputWithLabel(
                            controller: _phoneController,
                            label: "Payer's Phone number",
                            inputHintText: 'Enter phone number',
                            inputType: InputType.text,
                          ),
                          SizedBox(height: AppDimensions.height20),
                          FormInputWithLabel(
                            controller: _amountController,
                            label: 'Total Amount',
                            inputHintText: 'Enter amount',
                            inputType: InputType.number,
                          ),
                        ],
                      ),
                    ),
                  ),
                  SizedBox(height: AppDimensions.height35),
                  controller.isMakingPayment
                      ? const AppButtonLoading()
                      : ElevatedButton(
                          style: ThemeInputStyle.primaryButtonStyle,
                          child: TextSmall(
                            text: 'Make Payment',
                            size: AppDimensions.fontSize16,
                            color: ThemeColorStyle.appWhite,
                            weight: FontWeight.w600,
                          ),
                          onPressed: () {
                            var isValid = _globalKey.currentState?.validate();
                            if (isValid!) {
                              _showMyDialog(controller);
                            }
                          },
                        ),
                ],
              ),
            ),
          ),
        ),
      );
    });
  }

  void onNavigateToAddItemsScreen() {
    HelperUtils.popBeforeNavigateTo(AppRoutes.paymentAddServiceItemsScreen);
  }

  Future<void> _showMyDialog(PaymentController paymentController) async {
    return showDialog<void>(
      context: context,
      barrierDismissible: false, // user must tap button!
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text('Make Payment?'),
          content: SingleChildScrollView(
            child: ListBody(
              children: const <Widget>[
                Text('Are you sure ?'),
                Text('This action cannot be reverted'),
              ],
            ),
          ),
          actions: <Widget>[
            TextButton(
              child: const TextSmall(text: 'Cancel', color: ThemeColorStyle.appRed),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
            TextButton(
              child: const TextSmall(text: 'Approve', color: ThemeColorStyle.appBlue),
              onPressed: () => _onApproveButtonClick(paymentController),
            ),
          ],
        );
      },
    );
  }

  Future<void> _onApproveButtonClick(PaymentController paymentController) async {
    Navigator.of(context).pop();
    var name = _nameController.text;
    var phone = _phoneController.text;
    var amount = _amountController.text;
    paymentController.handleServicePayment(name: name, phone: phone, amount: amount);
  }
}
