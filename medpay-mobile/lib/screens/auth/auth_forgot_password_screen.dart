import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/app_routes.dart';
import 'package:medpay/utils/helper_utils.dart';
import 'package:medpay/utils/style_utils.dart';
import 'package:medpay/widgets/form_input_with_label.dart';
import 'package:medpay/widgets/hero_auth.dart';
import 'package:medpay/widgets/text_medium.dart';
import 'package:medpay/widgets/text_small.dart';

class AuthForgotPasswordScreen extends StatefulWidget {
  const AuthForgotPasswordScreen({Key? key}) : super(key: key);

  @override
  State<AuthForgotPasswordScreen> createState() => _AuthForgotPasswordScreen();
}

class _AuthForgotPasswordScreen extends State<AuthForgotPasswordScreen> {
  bool hidePassword = true;
  final TextEditingController _emailEditingController = TextEditingController();
  final GlobalKey<FormState> _globalKey = GlobalKey();

  @override
  void dispose() {
    _emailEditingController.dispose();
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
              const HeroAuth(heroTitle: "Forgot Password"),
              SizedBox(height: AppDimensions.height25),
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
                        TextMedium(
                          text: 'Please, enter your email to receive further '
                              'instructions on how to reset your password',
                          size: AppDimensions.fontSize14,
                          overflow: TextOverflow.visible,
                        ),
                        SizedBox(height: AppDimensions.height50),
                        FormInputWithLabel(
                          validator: (email) => HelperUtils.onValidateEmail(email),
                          controller: _emailEditingController,
                          label: 'Please,  Enter your email',
                          inputHintText: 'Enter your Email',
                          prefixIcon: Icons.email_outlined,
                          inputType: InputType.email,
                        ),
                      ],
                    )),
              ),
              SizedBox(height: AppDimensions.height35),
              // Verify email button
              ElevatedButton(
                style: ThemeInputStyle.primaryButtonStyle,
                child: TextSmall(
                  text: 'Next',
                  size: AppDimensions.fontSize16,
                  color: ThemeColorStyle.appWhite,
                  weight: FontWeight.w600,
                ),
                onPressed: () {
                  bool? isValid = _globalKey.currentState?.validate();
                  if (isValid!) {
                    print(_emailEditingController.text);
                    HelperUtils.popBeforeNavigateTo(AppRoutes.authVerifyEmailScreen);
                  }
                },
              ),
              SizedBox(height: AppDimensions.height10),
              TextButton(
                style: ThemeInputStyle.primaryTextButtonStyle,
                child: TextSmall(
                  text: 'Return to Login',
                  color: ThemeColorStyle.appBlue,
                  size: AppDimensions.fontSize14,
                  align: TextAlign.right,
                ),
                onPressed: () => HelperUtils.replaceWith(AppRoutes.authLoginScreen),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
