import 'package:flutter/material.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/style_utils.dart';

class AppPageLoading extends StatelessWidget {
  const AppPageLoading({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Container(
        padding: EdgeInsets.all(AppDimensions.height10),
        decoration: const BoxDecoration(
          color: ThemeColorStyle.appBlue,
          shape: BoxShape.circle,
        ),
        child: const CircularProgressIndicator(
          color: ThemeColorStyle.appWhite,
        ),
      ),
    );
  }
}
