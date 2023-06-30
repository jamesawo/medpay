import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:get/get.dart';
import 'package:medpay/data/constants/app_constants.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/app_routes.dart';
import 'package:medpay/utils/app_shared_preference.dart';
import 'package:medpay/utils/helper_utils.dart';
import 'package:medpay/utils/style_utils.dart';
import 'package:medpay/widgets/onboard_page.dart';
import 'package:medpay/widgets/text_small.dart';
import 'package:smooth_page_indicator/smooth_page_indicator.dart';

class OnBoardingScreen extends StatefulWidget {
  const OnBoardingScreen({Key? key}) : super(key: key);

  @override
  _OnBoardingScreenState createState() => _OnBoardingScreenState();
}

class _OnBoardingScreenState extends State<OnBoardingScreen> {
  final PageController _pageViewController = PageController();
  int currentPage = 0;

  @override
  void initState() {
    super.initState();
    setState(() {
      currentPage = 0;
    });
  }

  @override
  void dispose() {
    _pageViewController.dispose();
    super.dispose();
  }

  void saveSeenOnboarding() {
    Get.find<AppSharedPreference>().storeValue(AppConstants.keySeenOnboarding, "true");
  }

  @override
  Widget build(BuildContext context) {
    return AnnotatedRegion<SystemUiOverlayStyle>(
      value: SystemUiOverlayStyle.dark,
      child: Scaffold(
        body: SafeArea(
          child: PageView(
            controller: _pageViewController,
            onPageChanged: (page) {
              setState(() {
                currentPage = page;
              });
            },
            children: const [
              OnBoardingPage(
                pageTitle: 'Pay Medical \n Bill With Ease',
                pageDescription: 'Pay your medical bill \n yourself and at your comfort',
                pageImageUrl: 'assets/images/onboarding-a.png',
              ),
              OnBoardingPage(
                pageTitle: 'View Payment \n Transactions',
                pageDescription: 'View and download your \n previous and current medical \n bill transactions',
                pageImageUrl: 'assets/images/onboarding-b.png',
              ),
              OnBoardingPage(
                pageTitle: 'Download \n Your Receipt',
                pageDescription: 'Download your receipt and \n receive it via email',
                pageImageUrl: 'assets/images/onboarding-c.png',
              ),
            ],
          ),
        ),
        bottomSheet: Container(
          padding: EdgeInsets.symmetric(horizontal: AppDimensions.width10, vertical: AppDimensions.width10),
          color: ThemeColorStyle.appWhite,
          height: AppDimensions.height120,
          child: Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Column(
                children: [
                  SmoothPageIndicator(
                    controller: _pageViewController,
                    count: 3,
                    effect: const SlideEffect(
                        spacing: 8.0,
                        radius: 35.0,
                        dotWidth: 15.0,
                        dotHeight: 5.0,
                        paintStyle: PaintingStyle.stroke,
                        strokeWidth: 0.5,
                        dotColor: Colors.grey,
                        activeDotColor: ThemeColorStyle.appBlue),
                  ),
                  SizedBox(height: AppDimensions.height15),
                  ElevatedButton(
                    onPressed: () {
                      if (currentPage == 2) {
                        saveSeenOnboarding();
                        HelperUtils.popBeforeNavigateTo(AppRoutes.authLoginScreen);
                      } else {
                        setState(() => currentPage = currentPage + 1);
                        _pageViewController.animateToPage(
                          currentPage,
                          duration: const Duration(milliseconds: 400),
                          curve: Curves.easeInOut,
                        );
                      }
                    },
                    style: ThemeInputStyle.primaryButtonStyle,
                    child: TextSmall(
                      text: currentPage == 2 ? 'Continue' : 'Next',
                      color: ThemeColorStyle.appWhite,
                    ),
                  )
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }
}
