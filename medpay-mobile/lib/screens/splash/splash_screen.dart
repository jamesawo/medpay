import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:flutter_svg/svg.dart';
import 'package:get/get.dart';
import 'package:medpay/data/constants/app_constants.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/app_routes.dart';
import 'package:medpay/utils/app_shared_preference.dart';
import 'package:medpay/utils/helper_utils.dart';
import 'package:medpay/utils/style_utils.dart';
import 'package:medpay/widgets/text_large.dart';


class SplashScreen extends StatefulWidget {
  const SplashScreen({Key? key}) : super(key: key);

  @override
  State<SplashScreen> createState() => _SplashScreenState();
}

class _SplashScreenState extends State<SplashScreen> with TickerProviderStateMixin {
  late Animation<double> animation;
  late AnimationController animationController;

  @override
  void initState() {
    super.initState();
    animationController = AnimationController(vsync: this, duration: const Duration(seconds: 2))..forward();
    animation = CurvedAnimation(parent: animationController, curve: Curves.bounceIn);

    Timer(const Duration(seconds: 3), () => decideNextScreen());
  }

  @override
  void dispose() {
    animationController.dispose();
    super.dispose();
  }

  void decideNextScreen() {
    var seenOnboarding = Get.find<AppSharedPreference>().getValue(AppConstants.keySeenOnboarding);
    if (seenOnboarding != null) {
      HelperUtils.popBeforeNavigateTo(AppRoutes.authLoginScreen);
    } else {
      HelperUtils.popBeforeNavigateTo(AppRoutes.onboardingScreen);
    }
  }

  @override
  Widget build(BuildContext context) {
    return AnnotatedRegion<SystemUiOverlayStyle>(
      value: SystemUiOverlayStyle.dark,
      child: Scaffold(
        body: SizedBox(
          width: double.maxFinite,
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.center,
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Container(
                padding: EdgeInsets.only(left: AppDimensions.width10),
                child: SvgPicture.asset(
                  "assets/images/logo_blue.svg",
                  key: UniqueKey(),
                  fit: BoxFit.fill,
                  cacheColorFilter: true,
                  semanticsLabel: 'MedPay Logo',
                ),
              ),
              const TextLarge(
                text: "MedPay",
                align: TextAlign.center,
                color: ThemeColorStyle.appBlue,
                style: FontStyle.normal,
                fontFamily: ThemeFontFamily.secondaryFontFamily,
                weight: FontWeight.bold,
              ),
              SizedBox(height: AppDimensions.height20),
              SizedBox(
                width: AppDimensions.width80,
                child: const LinearProgressIndicator(),
              )
            ],
          ),
        ),
      ),
    );
  }
}
