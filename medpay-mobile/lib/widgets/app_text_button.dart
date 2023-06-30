import 'package:flutter/material.dart';
import 'package:medpay/utils/app_dimensions.dart';

class AppTextButton extends StatelessWidget {
  final Widget? child;
  final VoidCallback? action;
  final Color? buttonColor;

  const AppTextButton({
    this.child = const Icon(Icons.search),
    this.action,
    this.buttonColor = Colors.white,
    Key? key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return ElevatedButton(
      child: child,
      style: TextButton.styleFrom(
        backgroundColor: buttonColor,
        padding: EdgeInsets.symmetric(horizontal: AppDimensions.width10),
        tapTargetSize: MaterialTapTargetSize.shrinkWrap,
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(AppDimensions.radius7),
        ),
      ),
      onPressed: action ??
          () {
            return;
          },
    );
  }
}
