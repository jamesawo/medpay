import 'package:flutter/material.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/style_utils.dart';
import 'package:medpay/widgets/text_medium.dart';

class AppButtonLoading extends StatelessWidget {
  const AppButtonLoading({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: EdgeInsets.all(AppDimensions.height10),
      width: AppDimensions.width273,
      height: AppDimensions.height40,
      decoration: BoxDecoration(
        color: ThemeColorStyle.appOrange,
        borderRadius: BorderRadius.circular(AppDimensions.radius12),
      ),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          SizedBox(
            width: AppDimensions.width25 - 3,
            height: AppDimensions.height20,
            child: const CircularProgressIndicator(
              color: ThemeColorStyle.appWhite,
              strokeWidth: 2,
            ),
          ),
          SizedBox(width: AppDimensions.width10),
          TextMedium(
            weight: FontWeight.w500,
            text: 'Please wait...',
            color: ThemeColorStyle.appWhite,
            size: AppDimensions.fontSize16,
            // align: TextAlign.center,
          ),
        ],
      ),
    );
  }
}
