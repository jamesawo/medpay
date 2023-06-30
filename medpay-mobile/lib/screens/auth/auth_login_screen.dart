import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:get/get.dart';
import 'package:medpay/controllers/auth_controller.dart';
import 'package:medpay/data/enums/app_enum.dart';
import 'package:medpay/data/payload/login_payload.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/app_routes.dart';
import 'package:medpay/utils/helper_utils.dart';
import 'package:medpay/utils/style_utils.dart';
import 'package:medpay/widgets/app_button_loading.dart';
import 'package:medpay/widgets/continue_with_google.dart';
import 'package:medpay/widgets/form_input_with_label.dart';
import 'package:medpay/widgets/hero_auth.dart';
import 'package:medpay/widgets/text_small.dart';

class AuthLoginScreen extends StatefulWidget {
  const AuthLoginScreen({Key? key}) : super(key: key);

  @override
  State<AuthLoginScreen> createState() => _AuthLoginScreenState();
}

class _AuthLoginScreenState extends State<AuthLoginScreen> {
  final TextEditingController _emailController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();
  final GlobalKey<FormState> _globalKey = GlobalKey();

  @override
  void initState() {
    super.initState();
  }

  @override
  void dispose() {
    _emailController.dispose();
    _passwordController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return GetBuilder<AuthController>(builder: (controller) {
      return AnnotatedRegion<SystemUiOverlayStyle>(
        value: SystemUiOverlayStyle.light,
        child: Scaffold(
          resizeToAvoidBottomInset: true,
          backgroundColor: ThemeColorStyle.appWhite,
          body: SingleChildScrollView(
            scrollDirection: Axis.vertical,
            reverse: false,
            child: Column(
              children: [
                const HeroAuth(heroTitle: "Login"),
                SizedBox(height: AppDimensions.height25),
                Form(
                  key: _globalKey,
                  child: Padding(
                    padding: EdgeInsets.only(left: AppDimensions.width50, right: AppDimensions.width50),
                    child: Column(
                      children: [
                        FormInputWithLabel(
                          prefixIcon: Icons.email_rounded,
                          label: 'Email or Username',
                          inputType: InputType.email,
                          inputHintText: 'Enter your Email or Username',
                          controller: _emailController,
                        ),
                        SizedBox(height: AppDimensions.height20),
                        FormInputWithLabel(
                          validator: (value) => HelperUtils.onValidatePassword(value),
                          prefixIcon: Icons.lock_outline,
                          label: 'Password',
                          inputType: InputType.password,
                          inputHintText: 'Enter your Password',
                          suffixIcon: Icons.lock_outline,
                          controller: _passwordController,
                        ),
                        SizedBox(height: AppDimensions.height5),
                        Align(
                          alignment: AlignmentDirectional.topEnd,
                          child: TextButton(
                            style: ThemeInputStyle.primaryTextButtonStyle,
                            onPressed: () => HelperUtils.navigateTo(AppRoutes.authForgotPasswordScreen),
                            child: TextSmall(
                              text: 'Forgot Password?',
                              color: ThemeColorStyle.appBlue,
                              size: AppDimensions.fontSize14,
                              align: TextAlign.right,
                            ),
                          ),
                        ),
                      ],
                    ),
                  ),
                ),
                SizedBox(height: AppDimensions.height35),
                Column(
                  children: [
                    // login button
                    controller.authStatus == AuthStatusEnum.authenticating
                        ? const AppButtonLoading()
                        : ElevatedButton(
                            style: ThemeInputStyle.primaryButtonStyle,
                            child: TextSmall(
                              text: 'Login',
                              size: AppDimensions.fontSize16,
                              color: ThemeColorStyle.appWhite,
                              weight: FontWeight.w600,
                            ),
                            onPressed: () {
                              bool? isValid = _globalKey.currentState?.validate();
                              if (isValid! == true) {
                                String username = _emailController.text;
                                String password = _passwordController.text;
                                controller.doLogin(LoginPayload(username, password));
                              }
                            },
                          ),

                    SizedBox(height: AppDimensions.height10),
                    // continue with google widget
                    const ContinueWithGoogle(),
                    SizedBox(height: AppDimensions.height10),
                    // don't have an account
                    Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        TextSmall(
                          text: 'Don\'t have an account? ',
                          size: AppDimensions.fontSize14,
                        ),
                        TextButton(
                          style: ThemeInputStyle.primaryTextButtonStyle,
                          child: TextSmall(
                            text: 'Register',
                            color: ThemeColorStyle.appBlue,
                            size: AppDimensions.fontSize14,
                            align: TextAlign.right,
                          ),
                          onPressed: () => HelperUtils.navigateTo(AppRoutes.authRegisterScreen),
                        ),
                      ],
                    )
                  ],
                )
              ],
            ),
          ),
        ),
      );
    });
  }
}
