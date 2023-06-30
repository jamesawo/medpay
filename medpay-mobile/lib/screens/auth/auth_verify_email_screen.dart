import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/app_routes.dart';
import 'package:medpay/utils/helper_utils.dart';
import 'package:medpay/utils/style_utils.dart';
import 'package:medpay/widgets/form_input_with_label.dart';
import 'package:medpay/widgets/hero_auth.dart';
import 'package:medpay/widgets/text_small.dart';

class AuthVerifyEmailScreen extends StatefulWidget {
  const AuthVerifyEmailScreen({Key? key}) : super(key: key);

  @override
  State<AuthVerifyEmailScreen> createState() => _AuthVerifyEmailScreenState();
}

class _AuthVerifyEmailScreenState extends State<AuthVerifyEmailScreen> {
  bool hidePassword = true;
  final TextEditingController _codeController = TextEditingController();
  final GlobalKey<FormState> _globalKey = GlobalKey();

  @override
  void initState() {
    super.initState();
    _codeController.text = '593039';
  }

  @override
  void dispose() {
    _codeController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return AnnotatedRegion<SystemUiOverlayStyle>(
      value: SystemUiOverlayStyle.light,
      child: Scaffold(
        resizeToAvoidBottomInset: true,
        backgroundColor: Colors.white,
        body: SingleChildScrollView(
          scrollDirection: Axis.vertical,
          reverse: false,
          child: Column(
            children: [
              const HeroAuth(heroTitle: "Verify Email"),
              SizedBox(height: AppDimensions.height25),
              Padding(
                padding: EdgeInsets.only(
                  left: AppDimensions.width50,
                  right: AppDimensions.width50,
                ),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    RichText(
                      text: TextSpan(
                        children: [
                          WidgetSpan(
                            child: TextSmall(
                              text: 'We sent a five digit verification code to this email:',
                              size: AppDimensions.fontSize14,
                              overflow: TextOverflow.visible,
                            ),
                          ),
                          WidgetSpan(
                            child: GestureDetector(
                              child: TextSmall(
                                text: 'johndoe@gmail.com',
                                size: AppDimensions.fontSize14,
                                color: ThemeColorStyle.appBlue,
                              ),
                              onTap: () {
                                HelperUtils.replaceWith(AppRoutes.authUpdateEmailScreen);
                              },
                            ),
                          ),
                          WidgetSpan(
                            child: GestureDetector(
                              child: Icon(
                                Icons.edit,
                                size: AppDimensions.fontSize18,
                                color: ThemeColorStyle.appOrange,
                              ),
                              onTap: () => {HelperUtils.replaceWith(AppRoutes.authUpdateEmailScreen)},
                            ),
                          ),
                        ],
                      ),
                    ),
                    SizedBox(height: AppDimensions.height40),
                    Form(
                      key: _globalKey,
                      child: FormInputWithLabel(
                        validator: (value) => HelperUtils.onValidateText(value, 'Code'),
                        controller: _codeController,
                        label: 'Please,  Enter your code below',
                        inputType: InputType.code,
                      ),
                    )
                  ],
                ),
              ),
              SizedBox(height: AppDimensions.height35),
              ElevatedButton(
                style: ThemeInputStyle.primaryButtonStyle,
                child: TextSmall(
                  text: 'Verify Email',
                  size: AppDimensions.fontSize16,
                  color: ThemeColorStyle.appWhite,
                  weight: FontWeight.w600,
                ),
                onPressed: () {
                  var isValid = _globalKey.currentState?.validate();
                  if (isValid!) {
                    print(_codeController.text);
                    HelperUtils.popBeforeNavigateTo(AppRoutes.authLoginScreen);
                  }
                },
              ),
              SizedBox(height: AppDimensions.height10),
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  TextSmall(
                    text: 'Didn\'t Receive Code ? ',
                    size: AppDimensions.fontSize14,
                  ),
                  TextButton(
                    style: ThemeInputStyle.primaryTextButtonStyle,
                    child: TextSmall(
                      text: 'Resend',
                      color: ThemeColorStyle.appBlue,
                      size: AppDimensions.fontSize14,
                      align: TextAlign.right,
                    ),
                    onPressed: () => {},
                  ),
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }
}
