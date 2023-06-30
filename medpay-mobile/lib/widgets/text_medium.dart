import 'package:flutter/material.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/style_utils.dart';

class TextMedium extends StatelessWidget {
  final String text;
  final Color? color;
  final double? size;
  final FontStyle? style;
  final TextOverflow? overflow;
  final FontWeight? weight;
  final TextAlign align;

  const TextMedium({
    Key? key,
    required this.text,
    this.color = Colors.black,
    this.size = 24,
    this.style = FontStyle.normal,
    this.overflow = TextOverflow.ellipsis,
    this.weight = FontWeight.w400,
    this.align = TextAlign.left,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Text(
      text,
      overflow: overflow,
      textAlign: align,
      style: TextStyle(
        fontFamily: ThemeFontFamily.primaryFontFamily,
        fontWeight: weight,
        fontSize: size != null ? size! : AppDimensions.fontSize24,
        fontStyle: style,
        color: color,
      ),
    );
  }
}
