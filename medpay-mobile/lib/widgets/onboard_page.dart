import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:medpay/data/constants/app_constants.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/app_routes.dart';
import 'package:medpay/utils/app_shared_preference.dart';
import 'package:medpay/utils/helper_utils.dart';
import 'package:medpay/utils/style_utils.dart';
import 'package:medpay/widgets/text_large.dart';
import 'package:medpay/widgets/text_small.dart';

class OnBoardingPage extends StatelessWidget {
  final String pageTitle;
  final String pageDescription;
  final String pageImageUrl;

  const OnBoardingPage({required this.pageTitle, required this.pageDescription, required this.pageImageUrl, Key? key})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      width: double.maxFinite,
      color: ThemeColorStyle.appWhite,
      child: Column(
        children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.end,
            children: [
              TextButton(
                child: TextSmall(
                  text: "Skip",
                  size: AppDimensions.fontSize14,
                  weight: FontWeight.w600,
                ),
                onPressed: () {
                  Get.find<AppSharedPreference>().storeValue(AppConstants.keySeenOnboarding, "true");
                  HelperUtils.popBeforeNavigateTo(AppRoutes.authLoginScreen);
                },
              ),
            ],
          ),
          Column(
            children: [
              SizedBox(height: AppDimensions.height20),
              TextLarge(
                align: TextAlign.center,
                text: pageTitle,
                size: AppDimensions.fontSize40,
              ),
              SizedBox(height: AppDimensions.height30),
              Container(
                height: AppDimensions.height210,
                margin: EdgeInsets.only(left: AppDimensions.width20, right: AppDimensions.width20),
                decoration: BoxDecoration(
                  image: DecorationImage(
                    image: AssetImage(pageImageUrl),
                    fit: BoxFit.contain,
                  ),
                ),
              ),
              SizedBox(height: AppDimensions.height30),
              TextSmall(
                text: pageDescription,
                align: TextAlign.center,
              ),
            ],
          )
        ],
      ),
    );
  }
}
