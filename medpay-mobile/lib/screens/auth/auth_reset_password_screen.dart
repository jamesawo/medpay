import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/style_utils.dart';
import 'package:medpay/widgets/form_input_with_label.dart';
import 'package:medpay/widgets/hero_auth.dart';
import 'package:medpay/widgets/text_small.dart';

class AuthResetPasswordScreen extends StatefulWidget {
  const AuthResetPasswordScreen({Key? key}) : super(key: key);

  @override
  State<AuthResetPasswordScreen> createState() => _AuthResetPasswordScreenState();
}

class _AuthResetPasswordScreenState extends State<AuthResetPasswordScreen> {
  final TextEditingController _passwordController = TextEditingController();
  final TextEditingController _confirmPasswordController = TextEditingController();
  final GlobalKey<FormState> _globalKey = GlobalKey();

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
              const HeroAuth(heroTitle: "Reset Password"),
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
                      FormInputWithLabel(
                        controller: _passwordController,
                        label: 'Password',
                        inputHintText: 'Enter your Password',
                        suffixIcon: Icons.remove_red_eye_outlined,
                        inputType: InputType.password,
                        prefixIcon: Icons.lock_outline,
                      ),
                      SizedBox(height: AppDimensions.height20),
                      FormInputWithLabel(
                        controller: _confirmPasswordController,
                        label: 'Confirm Password',
                        inputHintText: 'Enter your Confirm Password',
                        prefixIcon: Icons.lock_outline,
                        suffixIcon: Icons.remove_red_eye_outlined,
                        inputType: InputType.password,
                      ),
                    ],
                  ),
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
                    print(_passwordController.text);
                    print(_confirmPasswordController.text);
                  }
                },
              ),
              SizedBox(height: AppDimensions.height10),
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  TextSmall(
                    text: 'Didn\'t Receive Code? ',
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
