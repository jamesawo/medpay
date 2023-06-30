import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/style_utils.dart';
import 'package:medpay/widgets/app_top_title.dart';
import 'package:medpay/widgets/form_input_with_label.dart';
import 'package:medpay/widgets/text_small.dart';

//todo:: delete class, possible unused.
class PaymentCreditCardScreen extends StatefulWidget {
  const PaymentCreditCardScreen({Key? key}) : super(key: key);

  @override
  State<PaymentCreditCardScreen> createState() => _PaymentCreditCardScreenState();
}

class _PaymentCreditCardScreenState extends State<PaymentCreditCardScreen> {
  final TextEditingController _cardNumber = TextEditingController();
  final TextEditingController _cardEx = TextEditingController();
  final TextEditingController _cardCv = TextEditingController();

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
                const AppTopTitle(title: 'Payment'),
                SizedBox(height: AppDimensions.height25),
                Container(
                  height: AppDimensions.height30,
                  decoration: const BoxDecoration(
                    image: DecorationImage(
                      image: AssetImage('assets/images/payment-method.png'),
                      fit: BoxFit.contain,
                    ),
                  ),
                ),
                SizedBox(height: AppDimensions.height25),
                Container(
                  margin: EdgeInsets.symmetric(horizontal: AppDimensions.width25),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      FormInputWithLabel(
                        controller: _cardNumber,
                        label: 'Card Number',
                      ),
                      SizedBox(height: AppDimensions.height20),
                      Row(
                        children: [
                          Expanded(
                            child: FormInputWithLabel(
                              controller: _cardEx,
                              label: 'Expiry Date',
                              inputHintText: 'MM/YY',
                            ),
                          ),
                          SizedBox(width: AppDimensions.width30),
                          Expanded(
                            child: FormInputWithLabel(
                              controller: _cardCv,
                              label: 'CVV',
                              inputHintText: '***',
                              // obscureText: true,
                              inputType: InputType.password,
                              suffixIcon: Icons.remove_red_eye,
                            ),
                          ),
                        ],
                      ),
                      SizedBox(height: AppDimensions.height30),
                      Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          const TextSmall(
                            text: 'Save Card Details',
                            weight: FontWeight.w700,
                          ),
                          ToggleButtons(children: [
                            SizedBox(
                              width: AppDimensions.width10,
                              child: const CircleAvatar(backgroundColor: ThemeColorStyle.appLightGreen),
                            ),
                            SizedBox(
                              width: AppDimensions.width10,
                              child: const CircleAvatar(backgroundColor: ThemeColorStyle.appRed),
                            ),
                          ], isSelected: const [
                            false,
                            true
                          ])
                        ],
                      ),
                      SizedBox(height: AppDimensions.height35),
                      Row(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          ElevatedButton(
                            style: ThemeInputStyle.primaryButtonStyle,
                            child: TextSmall(
                              text: 'Pay  ₦23,000',
                              size: AppDimensions.fontSize16,
                              color: ThemeColorStyle.appWhite,
                              weight: FontWeight.w600,
                            ),
                            onPressed: () {
                              print('Pay  ₦23,000');
                              // HelperUtils.navigateTo(AppRoutes.paymentConfirmScreen);
                            },
                          ),
                        ],
                      ),
                    ],
                  ),
                )

                // Image.asset('assets/images/payment-method.png'),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
