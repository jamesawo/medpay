import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/app_routes.dart';
import 'package:medpay/utils/helper_utils.dart';
import 'package:medpay/utils/style_utils.dart';
import 'package:medpay/widgets/app_icon.dart';
import 'package:medpay/widgets/app_top_title.dart';
import 'package:medpay/widgets/text_medium.dart';
import 'package:medpay/widgets/text_small.dart';

class SettingHomeScreen extends StatelessWidget {
  const SettingHomeScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return AnnotatedRegion<SystemUiOverlayStyle>(
      value: SystemUiOverlayStyle.dark,
      child: Scaffold(
        resizeToAvoidBottomInset: true,
        backgroundColor: Colors.white,
        body: SafeArea(
          child: SingleChildScrollView(
            scrollDirection: Axis.vertical,
            reverse: false,
            child: Column(
              children: [
                SizedBox(height: AppDimensions.height10),
                const AppTopTitle(title: 'Settings'),
                SizedBox(height: AppDimensions.height30),
                Column(children: [
                  // transaction date
                  Container(
                    padding: EdgeInsets.only(left: AppDimensions.width25),
                    width: double.maxFinite,
                    height: AppDimensions.height25,
                    color: ThemeColorStyle.appGray20,
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        TextSmall(
                          size: AppDimensions.fontSize12,
                          text: 'Theme',
                          weight: FontWeight.w600,
                        ),
                      ],
                    ),
                  ),
                  Padding(
                    padding: EdgeInsets.only(top: AppDimensions.height10, bottom: AppDimensions.height10),
                    child: ListTile(
                      contentPadding: EdgeInsets.only(left: AppDimensions.width25, right: AppDimensions.width25),
                      leading: AppIcon(
                        iconData: Icons.dark_mode,
                        shapeHeight: AppDimensions.width40,
                        shapeWidth: AppDimensions.width40,
                        shapeColor: ThemeColorStyle.appGray20,
                        iconColor: ThemeColorStyle.appBlue,
                        iconSize: AppDimensions.fontSize24,
                        shapeRadius: AppDimensions.radius12,
                      ),
                      title: TextMedium(
                        text: 'Dark Theme',
                        size: AppDimensions.fontSize16,
                        weight: FontWeight.w400,
                      ),
                      trailing: const Icon(
                        Icons.toggle_off,
                        color: ThemeColorStyle.appBlue10,
                      ),
                      onTap: () => print('Dark Theme'),
                    ),
                  ),
                  Container(
                    margin: EdgeInsets.symmetric(horizontal: AppDimensions.width25),
                    height: 1,
                    color: ThemeColorStyle.appGray20,
                  ),
                  Padding(
                    padding: EdgeInsets.only(top: AppDimensions.height10, bottom: AppDimensions.height10),
                    child: ListTile(
                      contentPadding: EdgeInsets.only(left: AppDimensions.width25, right: AppDimensions.width25),
                      leading: AppIcon(
                        iconData: Icons.dark_mode,
                        shapeHeight: AppDimensions.width40,
                        shapeWidth: AppDimensions.width40,
                        shapeColor: ThemeColorStyle.appGray20,
                        iconColor: ThemeColorStyle.appBlue,
                        iconSize: AppDimensions.fontSize24,
                        shapeRadius: AppDimensions.radius12,
                      ),
                      title: TextMedium(
                        text: 'Light Theme',
                        size: AppDimensions.fontSize16,
                        weight: FontWeight.w400,
                      ),
                      trailing: const Icon(
                        Icons.toggle_on,
                        color: ThemeColorStyle.appBlue,
                      ),
                      onTap: () => print('Light Theme'),
                    ),
                  ),
                  SizedBox(height: AppDimensions.height5),
                  Container(
                    padding: EdgeInsets.only(left: AppDimensions.width25),
                    width: double.maxFinite,
                    height: AppDimensions.height25,
                    color: ThemeColorStyle.appGray20,
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        TextSmall(
                          size: AppDimensions.fontSize12,
                          text: 'About',
                          weight: FontWeight.w600,
                        ),
                      ],
                    ),
                  ),
                  Container(
                    margin: EdgeInsets.symmetric(horizontal: AppDimensions.width25),
                    height: 1,
                    color: ThemeColorStyle.appGray20,
                  ),
                  Padding(
                    padding: EdgeInsets.only(top: AppDimensions.height10, bottom: AppDimensions.height10),
                    child: ListTile(
                      contentPadding: EdgeInsets.only(left: AppDimensions.width25, right: AppDimensions.width25),
                      leading: AppIcon(
                        iconData: Icons.info,
                        shapeHeight: AppDimensions.width40,
                        shapeWidth: AppDimensions.width40,
                        shapeColor: ThemeColorStyle.appGray20,
                        iconColor: ThemeColorStyle.appBlue,
                        iconSize: AppDimensions.fontSize24,
                        shapeRadius: AppDimensions.radius12,
                      ),
                      title: TextMedium(
                        text: 'About Us',
                        size: AppDimensions.fontSize16,
                        weight: FontWeight.w400,
                      ),
                      trailing: AppIcon(
                        shapeColor: ThemeColorStyle.appLightBlue,
                        iconData: Icons.keyboard_arrow_down_outlined,
                        iconColor: ThemeColorStyle.appWhite,
                        iconSize: AppDimensions.fontSize24,
                        shapeRadius: AppDimensions.radius100,
                        shapeHeight: AppDimensions.height25,
                        shapeWidth: AppDimensions.width30,
                        // shapeRadius: 100,
                      ),
                      onTap: () => HelperUtils.navigateTo(AppRoutes.settingAboutScreen),
                    ),
                  ),
                  Container(
                    margin: EdgeInsets.symmetric(horizontal: AppDimensions.width25),
                    height: 1,
                    color: ThemeColorStyle.appGray20,
                  ),
                  Padding(
                    padding: EdgeInsets.only(top: AppDimensions.height10, bottom: AppDimensions.height10),
                    child: ListTile(
                      contentPadding: EdgeInsets.only(left: AppDimensions.width25, right: AppDimensions.width25),
                      leading: AppIcon(
                        iconData: Icons.help,
                        shapeHeight: AppDimensions.width40,
                        shapeWidth: AppDimensions.width40,
                        shapeColor: ThemeColorStyle.appGray20,
                        iconColor: ThemeColorStyle.appBlue,
                        iconSize: AppDimensions.fontSize24,
                        shapeRadius: AppDimensions.radius12,
                      ),
                      title: TextMedium(
                        text: 'Terms of Service',
                        size: AppDimensions.fontSize16,
                        weight: FontWeight.w400,
                      ),
                      trailing: AppIcon(
                        shapeColor: ThemeColorStyle.appLightBlue,
                        iconData: Icons.keyboard_arrow_down_outlined,
                        iconColor: ThemeColorStyle.appWhite,
                        iconSize: AppDimensions.fontSize24,
                        shapeRadius: AppDimensions.radius100,
                        shapeHeight: AppDimensions.height25,
                        shapeWidth: AppDimensions.width30,
                        // shapeRadius: 100,
                      ),
                      onTap: () => HelperUtils.navigateTo(AppRoutes.settingTermsOfServiceScreen),
                    ),
                  ),
                  Container(
                    margin: EdgeInsets.symmetric(horizontal: AppDimensions.width25),
                    height: 1,
                    color: ThemeColorStyle.appGray20,
                  ),

                  Padding(
                    padding: EdgeInsets.only(top: AppDimensions.height10, bottom: AppDimensions.height10),
                    child: ListTile(
                      contentPadding: EdgeInsets.only(left: AppDimensions.width25, right: AppDimensions.width25),
                      leading: AppIcon(
                        iconData: Icons.policy,
                        shapeHeight: AppDimensions.width40,
                        shapeWidth: AppDimensions.width40,
                        shapeColor: ThemeColorStyle.appGray20,
                        iconColor: ThemeColorStyle.appBlue,
                        iconSize: AppDimensions.fontSize24,
                        shapeRadius: AppDimensions.radius12,
                      ),
                      title: TextMedium(
                        text: 'Privacy Policy',
                        size: AppDimensions.fontSize16,
                        weight: FontWeight.w400,
                      ),
                      trailing: AppIcon(
                        shapeColor: ThemeColorStyle.appLightBlue,
                        iconData: Icons.keyboard_arrow_down_outlined,
                        iconColor: ThemeColorStyle.appWhite,
                        iconSize: AppDimensions.fontSize24,
                        shapeRadius: AppDimensions.radius100,
                        shapeHeight: AppDimensions.height25,
                        shapeWidth: AppDimensions.width30,
                        // shapeRadius: 100,
                      ),
                      onTap: () => HelperUtils.navigateTo(AppRoutes.settingPrivacyPolicyScreen),
                    ),
                  ),
                  Container(
                    margin: EdgeInsets.symmetric(horizontal: AppDimensions.width25),
                    height: 1,
                    color: ThemeColorStyle.appGray20,
                  ),
                  Padding(
                    padding: EdgeInsets.only(top: AppDimensions.height10, bottom: AppDimensions.height10),
                    child: ListTile(
                      contentPadding: EdgeInsets.only(left: AppDimensions.width25, right: AppDimensions.width25),
                      leading: AppIcon(
                        iconData: Icons.thumb_up,
                        shapeHeight: AppDimensions.width40,
                        shapeWidth: AppDimensions.width40,
                        shapeColor: ThemeColorStyle.appGray20,
                        iconColor: ThemeColorStyle.appBlue,
                        iconSize: AppDimensions.fontSize24,
                        shapeRadius: AppDimensions.radius12,
                      ),
                      title: TextMedium(
                        text: 'Rate us on Play Store',
                        size: AppDimensions.fontSize16,
                        weight: FontWeight.w400,
                      ),
                      trailing: AppIcon(
                        shapeColor: ThemeColorStyle.appLightBlue,
                        iconData: Icons.keyboard_arrow_down_outlined,
                        iconColor: ThemeColorStyle.appWhite,
                        iconSize: AppDimensions.fontSize24,
                        shapeRadius: AppDimensions.radius100,
                        shapeHeight: AppDimensions.height25,
                        shapeWidth: AppDimensions.width30,
                        // shapeRadius: 100,
                      ),
                      onTap: () => print('Rate us'),
                    ),
                  ),
                ]),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
