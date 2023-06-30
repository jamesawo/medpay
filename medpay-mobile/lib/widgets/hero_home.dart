import 'package:flutter/material.dart';
import 'package:medpay/data/constants/app_constants.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/style_utils.dart';
import 'package:medpay/widgets/text_large.dart';
import 'package:medpay/widgets/text_small.dart';

class HeroHome extends StatelessWidget {
  final String name;
  final String? greeting;
  final String? imageUrl;
  final String? salute;

  const HeroHome({
    required this.name,
    this.greeting = AppConstants.appGreeting,
    this.imageUrl,
    this.salute = AppConstants.appSalute,
    Key? key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      color: ThemeColorStyle.appBlue,
      height: AppDimensions.height120,
      padding: EdgeInsets.only(left: AppDimensions.width25, right: AppDimensions.width20, bottom: 0, top: AppDimensions.height10),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          Expanded(
            flex: 2,
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                TextLarge(
                  text: '$salute $name',
                  size: AppDimensions.fontSize22,
                  color: ThemeColorStyle.appWhite,
                  overflow: TextOverflow.clip,
                ),
                SizedBox(
                  height: AppDimensions.height2,
                ),
                TextSmall(
                  text: greeting!,
                  size: AppDimensions.fontSize16,
                  color: ThemeColorStyle.appWhite,
                ),
              ],
            ),
          ),
          Expanded(
            flex: 1,
            child: Container(
              height: AppDimensions.height50,
              decoration: BoxDecoration(
                image: DecorationImage(
                  image: AssetImage(imageUrl != null ? imageUrl! : AppConstants.appDefaultAvatarUri),
                  fit: BoxFit.contain,
                ),
                borderRadius: BorderRadius.circular(AppDimensions.radius100),
              ),
            ),
          ),
        ],
      ),
    );
  }
}
