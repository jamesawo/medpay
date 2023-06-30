import 'package:flutter/material.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/style_utils.dart';
import 'package:medpay/widgets/app_icon.dart';
import 'package:medpay/widgets/text_medium.dart';

class AppTopSliverTitle extends StatelessWidget {
  final String title;

  const AppTopSliverTitle({required this.title, Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: EdgeInsets.symmetric(horizontal: AppDimensions.width25),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.center,
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          AppIcon(
            shapeHeight: AppDimensions.height25,
            shapeWidth: AppDimensions.width30,
            iconData: Icons.arrow_back,
            shapeRadius: AppDimensions.radius32,
            shapeColor: ThemeColorStyle.appOrange,
          ),
          TextMedium(
            color: ThemeColorStyle.appWhite,
            text: title,
            align: TextAlign.center,
            weight: FontWeight.w600,
          ),
          AppIcon(
            shapeHeight: AppDimensions.height25,
            shapeWidth: AppDimensions.width30,
            iconData: Icons.more_vert,
            shapeRadius: AppDimensions.radius32,
            shapeColor: ThemeColorStyle.appOrange,
          ),
        ],
      ),
    );
  }
}
