import 'package:flutter/material.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/style_utils.dart';
import 'package:medpay/widgets/text_small.dart';

class HomeActionCard extends StatelessWidget {
  final String text;
  final IconData? icon;
  final VoidCallback? action;
  final Color? borderColor;

  const HomeActionCard({
    required this.text,
    this.icon = Icons.image,
    this.action,
    this.borderColor = const Color(0xFFD0E7F7),
    Key? key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Expanded(
      child: Card(
        shape: RoundedRectangleBorder(
          side: BorderSide(color: borderColor!),
          borderRadius: BorderRadius.circular(AppDimensions.radius7),
        ),
        color: ThemeColorStyle.appWhite,
        child: InkWell(
          onTap: action,
          child: Padding(
            padding: EdgeInsets.all(AppDimensions.height10),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                Icon(icon, color: ThemeColorStyle.appBlue),
                SizedBox(height: AppDimensions.height2),
                TextSmall(
                  size: AppDimensions.fontSize13,
                  text: text,
                  align: TextAlign.center,
                  weight: FontWeight.w500,
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
