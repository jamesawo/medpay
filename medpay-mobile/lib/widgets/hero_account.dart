import 'package:flutter/material.dart';
import 'package:medpay/data/constants/app_constants.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/style_utils.dart';
import 'package:medpay/widgets/text_large.dart';
import 'package:medpay/widgets/text_medium.dart';

import 'app_icon.dart';

class HeroAccount extends StatelessWidget {
  final String? title;
  final String? imageUrl;
  final double? preferredHeight;
  final String name;
  final String email;

  const HeroAccount({
    this.title = 'My Account',
    this.imageUrl,
    this.preferredHeight,
    required this.name,
    required this.email,
    Key? key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
        color: ThemeColorStyle.appBlue,
        height: preferredHeight ?? AppDimensions.height300,
        child: SizedBox(
          width: double.maxFinite,
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              TextLarge(
                size: AppDimensions.fontSize22,
                text: title!,
                color: ThemeColorStyle.appWhite,
              ),
              SizedBox(height: AppDimensions.height10),
              SizedBox(
                height: AppDimensions.height100,
                child: Stack(
                  alignment: AlignmentDirectional.center,
                  children: [
                    Positioned(
                      child: Container(
                        height: AppDimensions.height90,
                        decoration: BoxDecoration(
                          image: DecorationImage(
                            image: AssetImage(imageUrl != null ? imageUrl! : AppConstants.appDefaultAvatarUri),
                            fit: BoxFit.contain,
                          ),
                          borderRadius: BorderRadius.circular(AppDimensions.radius32),
                        ),
                      ),
                    ),
                    Positioned(
                      height: AppDimensions.height25,
                      bottom: 0,
                      right: AppDimensions.width150,
                      child: InkWell(
                        onTap: () {},
                        child: AppIcon(
                          shapeColor: ThemeColorStyle.appOrange,
                          iconSize: AppDimensions.fontSize16,
                          iconData: Icons.edit,
                          shapeWidth: AppDimensions.width30,
                        ),
                      ),
                    )
                  ],
                ),
              ),
              SizedBox(height: AppDimensions.height8),
              TextLarge(
                text: name,
                color: ThemeColorStyle.appWhite,
                size: AppDimensions.fontSize20,
              ),
              TextMedium(
                size: AppDimensions.fontSize18,
                text: email,
                color: ThemeColorStyle.appWhite,
              ),
            ],
          ),
        ));
  }
}
