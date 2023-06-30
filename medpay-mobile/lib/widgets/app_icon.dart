import 'package:flutter/material.dart';
import 'package:medpay/utils/app_dimensions.dart';

class AppIcon extends StatelessWidget {
  final Color? shapeColor;
  final double? shapeRadius;
  final IconData iconData;
  final double? iconSize;
  final Color? iconColor;
  final double? shapeHeight;
  final double? shapeWidth;
  final String? message;

  const AppIcon({
    this.shapeColor = Colors.blue,
    required this.iconData,
    this.shapeRadius,
    this.iconColor = Colors.white,
    this.iconSize,
    this.shapeHeight,
    this.shapeWidth,
    this.message,
    Key? key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      child: Center(
        child: Icon(
          iconData,
          size: iconSize ?? AppDimensions.fontSize14,
          color: iconColor,
        ),
      ),
      width: shapeWidth ?? AppDimensions.width30,
      height: shapeHeight ?? AppDimensions.height40,
      decoration: BoxDecoration(
        color: shapeColor,
        borderRadius: BorderRadius.circular(
          shapeRadius ?? AppDimensions.radius100,
        ),
      ),
    );
  }
}
