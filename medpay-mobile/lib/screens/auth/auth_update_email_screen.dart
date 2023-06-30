import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/helper_utils.dart';
import 'package:medpay/utils/style_utils.dart';
import 'package:medpay/widgets/form_input_with_label.dart';
import 'package:medpay/widgets/hero_auth.dart';
import 'package:medpay/widgets/text_small.dart';

class AuthUpdateEmailScreen extends StatefulWidget {
  const AuthUpdateEmailScreen({Key? key}) : super(key: key);

  @override
  State<AuthUpdateEmailScreen> createState() => _AuthUpdateEmailScreenState();
}

class _AuthUpdateEmailScreenState extends State<AuthUpdateEmailScreen> {
  bool hidePassword = true;
  final TextEditingController _emailController = TextEditingController();
  final GlobalKey<FormState> _globalKey = GlobalKey();

  @override
  void dispose() {
    _emailController.dispose();
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
              const HeroAuth(heroTitle: "Update Email"),
              SizedBox(height: AppDimensions.height25),
              Padding(
                padding: EdgeInsets.only(
                  left: AppDimensions.width50,
                  right: AppDimensions.width50,
                ),
                child: Form(
                  key: _globalKey,
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      FormInputWithLabel(
                        validator: (value) => HelperUtils.onValidateEmail(value),
                        controller: _emailController,
                        label: 'Please, enter your email below',
                        prefixIcon: Icons.email_outlined,
                        inputHintText: 'Enter your Email',
                      ),
                    ],
                  ),
                ),
              ),
              SizedBox(height: AppDimensions.height35),
              ElevatedButton(
                style: ThemeInputStyle.primaryButtonStyle,
                child: TextSmall(
                  text: 'Update',
                  size: AppDimensions.fontSize16,
                  color: ThemeColorStyle.appWhite,
                  weight: FontWeight.w600,
                ),
                onPressed: () {
                  var isValid = _globalKey.currentState?.validate();
                  if (isValid!) {
                    print(_emailController.text);
                  }
                },
              ),
              SizedBox(height: AppDimensions.height10),
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Icon(Icons.arrow_back_outlined, size: AppDimensions.fontSize18),
                  TextButton(
                    style: ThemeInputStyle.primaryTextButtonStyle,
                    child: TextSmall(
                      text: 'Go Back',
                      color: ThemeColorStyle.appBlue,
                      size: AppDimensions.fontSize14,
                      align: TextAlign.right,
                    ),
                    onPressed: () => HelperUtils.popOnly(),
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
