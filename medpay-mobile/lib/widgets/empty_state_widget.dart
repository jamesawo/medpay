import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/widgets/text_small.dart';

enum ImageStyle { style1, style2 }

class EmptyStateWidget extends StatelessWidget {
  final String? message;
  final ImageStyle? style;

  const EmptyStateWidget({
    this.message = 'No data yet!',
    this.style = ImageStyle.style1,
    Key? key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    String png = 'assets/images/empty_box_illustration.png';
    String svg = 'assets/images/empty_illustration.svg';
    String img = style == ImageStyle.style1 ? svg : png;

    return Column(
      children: [
        style == ImageStyle.style1
            ? SvgPicture.asset(
                img,
                key: key,
                fit: BoxFit.fill,
                cacheColorFilter: true,
                semanticsLabel: 'No Data',
              )
            : Container(
                height: AppDimensions.height150,
                decoration: BoxDecoration(
                  image: DecorationImage(
                    image: AssetImage(img),
                    fit: BoxFit.contain,
                  ),
                ),
              ),
        SizedBox(height: AppDimensions.height5),
        TextSmall(
          size: AppDimensions.fontSize12,
          text: message!,
        ),
      ],
    );
  }
}
