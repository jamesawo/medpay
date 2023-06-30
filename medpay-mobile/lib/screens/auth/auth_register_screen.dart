import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:get/get.dart';
import 'package:medpay/controllers/auth_controller.dart';
import 'package:medpay/data/enums/app_enum.dart';
import 'package:medpay/data/payload/register_payload.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/app_routes.dart';
import 'package:medpay/utils/helper_utils.dart';
import 'package:medpay/utils/style_utils.dart';
import 'package:medpay/widgets/app_button_loading.dart';
import 'package:medpay/widgets/continue_with_google.dart';
import 'package:medpay/widgets/form_input_with_label.dart';
import 'package:medpay/widgets/hero_auth.dart';
import 'package:medpay/widgets/text_small.dart';

class AuthRegisterScreen extends StatefulWidget {
  const AuthRegisterScreen({Key? key}) : super(key: key);

  @override
  State<AuthRegisterScreen> createState() => _AuthRegisterScreenState();
}

class _AuthRegisterScreenState extends State<AuthRegisterScreen> {
  final GlobalKey<FormState> _globalKey = GlobalKey();
  final TextEditingController _nameController = TextEditingController();
  final TextEditingController _emailController = TextEditingController();
  final TextEditingController _phoneNumberController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();
  AuthController authController = Get.find<AuthController>();

  @override
  void dispose() {
    _nameController.dispose();
    _emailController.dispose();
    _passwordController.dispose();
    _phoneNumberController.dispose();
    super.dispose();
  }

  /* Process user registration */
  void handleUserRegistration() {
    bool? isFormValid = _globalKey.currentState?.validate();
    if (isFormValid!) {
      String name = _nameController.text;
      String email = _emailController.text;
      String password = _passwordController.text;
      String phone = _phoneNumberController.text;
      RegisterPayload registerPayload = RegisterPayload.unnamed(name, phone, email, password);
      authController.doRegister(registerPayload);
    }
  }

  @override
  Widget build(BuildContext context) {
    return GetBuilder<AuthController>(
      builder: (authController) {
        return AnnotatedRegion<SystemUiOverlayStyle>(
          value: SystemUiOverlayStyle.light,
          child: Scaffold(
            resizeToAvoidBottomInset: true,
            backgroundColor: Colors.white,
            body: SingleChildScrollView(
              physics: const BouncingScrollPhysics(),
              scrollDirection: Axis.vertical,
              reverse: false,
              child: Column(
                children: [
                  const HeroAuth(heroTitle: "Register"),
                  SizedBox(height: AppDimensions.height25),
                  Form(
                    key: _globalKey,
                    child: Padding(
                      padding: EdgeInsets.only(left: AppDimensions.width50, right: AppDimensions.width50),
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          FormInputWithLabel(
                            prefixIcon: Icons.person_outline,
                            label: 'Name',
                            inputType: InputType.text,
                            controller: _nameController,
                            inputHintText: 'Enter your name',
                          ),
                          SizedBox(height: AppDimensions.height20),
                          // email
                          FormInputWithLabel(
                            prefixIcon: Icons.email_outlined,
                            label: 'Email',
                            inputType: InputType.email,
                            controller: _emailController,
                            inputHintText: 'Enter your Email',
                            // validator: (value) => HelperUtils.onValidateEmail(value),
                            useAutoValidate: true,
                          ),
                          SizedBox(height: AppDimensions.height20),
                          // phone
                          FormInputWithLabel(
                            prefixIcon: Icons.phone,
                            label: 'Phone',
                            inputType: InputType.number,
                            controller: _phoneNumberController,
                            inputHintText: 'Enter your Phone Number',
                          ),
                          SizedBox(height: AppDimensions.height20),
                          // password
                          FormInputWithLabel(
                            //validator: (value) => HelperUtils.onValidatePassword(value),
                            prefixIcon: Icons.lock_outline,
                            label: 'Password',
                            inputType: InputType.password,
                            controller: _passwordController,
                            inputHintText: 'Enter your Password',
                            suffixIcon: Icons.lock_outline,
                          ),
                        ],
                      ),
                    ),
                  ),
                  SizedBox(height: AppDimensions.height35),
                  Column(
                    children: [
                      // register button widget
                      authController.authStatus == AuthStatusEnum.authenticating
                          ? const AppButtonLoading()
                          : ElevatedButton(
                              onPressed: () {
                                handleUserRegistration();
                              },
                              style: ThemeInputStyle.primaryButtonStyle,
                              child: TextSmall(
                                text: 'Register',
                                size: AppDimensions.fontSize16,
                                color: ThemeColorStyle.appWhite,
                                weight: FontWeight.w600,
                              ),
                            ),

                      SizedBox(height: AppDimensions.height10),
                      // continue with google widget
                      const ContinueWithGoogle(),
                      SizedBox(height: AppDimensions.height10),
                      Row(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          TextSmall(
                            text: 'Already a member? ',
                            size: AppDimensions.fontSize14,
                          ),
                          TextButton(
                            style: ThemeInputStyle.primaryTextButtonStyle,
                            child: TextSmall(
                              text: 'Login',
                              color: ThemeColorStyle.appBlue,
                              size: AppDimensions.fontSize14,
                              align: TextAlign.right,
                            ),
                            onPressed: () => HelperUtils.navigateTo(AppRoutes.authLoginScreen),
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
      },
    );
  }
}
