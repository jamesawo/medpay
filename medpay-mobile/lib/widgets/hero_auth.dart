import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/style_utils.dart';
import 'package:medpay/widgets/text_large.dart';

class HeroAuth extends StatelessWidget {
  final String heroTitle;
  const HeroAuth({required this.heroTitle, Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      color: ThemeColorStyle.appBlue,
      padding: const EdgeInsets.only(bottom: 0, left: 0, right: 0),
      height: AppDimensions.height200,
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          SizedBox(height: AppDimensions.height40),
          SvgPicture.asset(
            "assets/images/logo_white.svg",
            fit: BoxFit.contain,
            width: AppDimensions.height90,
          ),
          SizedBox(height: AppDimensions.height20),
          Row(
            children: [
              Container(
                padding: EdgeInsets.only(left: AppDimensions.width50),
                child: TextLarge(
                  size: AppDimensions.fontSize28,
                  text: heroTitle,
                  color: ThemeColorStyle.appWhite,
                ),
              ),
            ],
          ),
        ],
      ),
    );
  }
}
