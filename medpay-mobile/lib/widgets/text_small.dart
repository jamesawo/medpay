import 'package:flutter/material.dart';
import 'package:medpay/utils/style_utils.dart';

class TextSmall extends StatelessWidget {
  final String text;
  final Color? color;
  final double? size;
  final FontStyle? style;
  final TextOverflow? overflow;
  final FontWeight? weight;
  final TextAlign? align;

  const TextSmall({
    Key? key,
    required this.text,
    this.color,
    this.size = 18,
    this.style = FontStyle.normal,
    this.overflow = TextOverflow.ellipsis,
    this.weight = FontWeight.w400,
    this.align = TextAlign.left,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Text(
      text,
      textAlign: align,
      overflow: overflow,
      style: TextStyle(
          fontFamily: ThemeFontFamily.primaryFontFamily,
          fontWeight: weight,
          fontSize: size,
          fontStyle: style,
          color: color ?? ThemeColorStyle.appBlack),
    );
  }
}
