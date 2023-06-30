import 'package:flutter/material.dart';
import 'package:medpay/utils/style_utils.dart';

class AppIconButton extends StatelessWidget {
  final Widget? child;
  final VoidCallback? action;

  const AppIconButton({
    this.child = const Icon(Icons.search),
    this.action,
    Key? key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return ElevatedButton(
      child: child,
      style: ThemeInputStyle.primaryIconButtonStyle,
      onPressed: action,
    );
  }
}
