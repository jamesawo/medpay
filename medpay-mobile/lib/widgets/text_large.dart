import 'package:flutter/material.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/style_utils.dart';

class TextLarge extends StatelessWidget {
  final String text;
  final Color? color;
  final double? size;
  final FontStyle? style;
  final TextOverflow? overflow;
  final FontWeight? weight;
  final TextAlign? align;
  final String? fontFamily;

  const TextLarge({
    Key? key,
    required this.text,
    this.color = Colors.black,
    this.size,
    this.style = FontStyle.normal,
    this.overflow = TextOverflow.ellipsis,
    this.weight = FontWeight.w600,
    this.align = TextAlign.left,
    this.fontFamily,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Text(
      text,
      overflow: overflow,
      textAlign: align,
      style: TextStyle(
          fontFamily: fontFamily ?? ThemeFontFamily.primaryFontFamily,
          fontWeight: weight,
          fontSize: size != null ? size! : AppDimensions.fontSize40,
          fontStyle: style,
          color: color),
    );
  }
}
