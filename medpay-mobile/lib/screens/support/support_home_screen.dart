import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:medpay/controllers/hospital_controller.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/app_routes.dart';
import 'package:medpay/utils/helper_utils.dart';
import 'package:medpay/utils/style_utils.dart';
import 'package:medpay/widgets/app_icon.dart';
import 'package:medpay/widgets/app_top_title.dart';
import 'package:medpay/widgets/text_medium.dart';
import 'package:medpay/widgets/text_small.dart';

class SupportHomeScreen extends StatefulWidget {
  const SupportHomeScreen({Key? key}) : super(key: key);

  @override
  State<SupportHomeScreen> createState() => _SupportHomeScreenState();
}

class _SupportHomeScreenState extends State<SupportHomeScreen> {
  late ScrollController scrollController;
  bool showTitle = false;

  @override
  void initState() {
    scrollController = ScrollController(initialScrollOffset: 1);
    scrollController.addListener(() {
      var offset = scrollController.offset;
      if (offset > 200 && showTitle != true) _setTitleState(true);
      if (offset < 190 && showTitle != false) _setTitleState(false);
    });
    super.initState();
  }

  void _setTitleState(bool value) {
    setState(() {
      showTitle = value;
    });
  }

  @override
  void dispose() {
    scrollController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: ThemeColorStyle.appWhite,
      body: SafeArea(
        child: CustomScrollView(
          controller: scrollController,
          shrinkWrap: true,
          slivers: [
            SliverAppBar(
              titleSpacing: 0,
              automaticallyImplyLeading: false,
              backgroundColor: ThemeColorStyle.appWhite,
              title: const AppTopTitle(title: 'Support', showPrefixIcon: false),
              pinned: true,
              stretch: true,
              scrolledUnderElevation: AppDimensions.height5,
              // floating: true,
              // snap: false,
            ),
            SliverToBoxAdapter(
              child: SizedBox(height: AppDimensions.height15),
            ),
            SliverList(
              delegate: SliverChildListDelegate(
                [
                  // help
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
                          text: 'Help',
                          weight: FontWeight.w600,
                        ),
                      ],
                    ),
                  ),
                  ListTile(
                    contentPadding: EdgeInsets.symmetric(
                      horizontal: AppDimensions.width25,
                      vertical: AppDimensions.height8,
                    ),
                    leading: AppIcon(
                      shapeHeight: AppDimensions.width40,
                      shapeWidth: AppDimensions.width40,
                      shapeColor: ThemeColorStyle.appGray20,
                      iconData: Icons.live_help,
                      iconColor: ThemeColorStyle.appBlue,
                      iconSize: AppDimensions.fontSize24,
                      shapeRadius: AppDimensions.radius12,
                    ),
                    title: TextMedium(
                      text: 'Live Chat',
                      size: AppDimensions.fontSize16,
                      weight: FontWeight.w400,
                    ),
                    trailing: Icon(
                      Icons.expand_circle_down_rounded,
                      color: ThemeColorStyle.appBlue10,
                      size: AppDimensions.fontSize24,
                    ),
                    onTap: () {},
                  ),
                  /*  Container(
                    margin: EdgeInsets.symmetric(horizontal: AppDimensions.width25),
                    height: AppDimensions.height2,
                    color: ThemeColorStyle.appGray20,
                  ),*/
                  /* ListTile(
                    contentPadding: EdgeInsets.symmetric(
                      horizontal: AppDimensions.width25,
                      vertical: AppDimensions.height8,
                    ),
                    leading: AppIcon(
                      shapeHeight: AppDimensions.width40,
                      shapeWidth: AppDimensions.width40,
                      shapeColor: ThemeColorStyle.appGray20,
                      iconData: Icons.contact_support,
                      iconColor: ThemeColorStyle.appBlue,
                      iconSize: AppDimensions.fontSize24,
                      shapeRadius: AppDimensions.radius12,
                    ),
                    title: TextMedium(
                      text: 'FAQ',
                      size: AppDimensions.fontSize16,
                      weight: FontWeight.w400,
                    ),
                    trailing: Icon(
                      Icons.expand_circle_down_rounded,
                      color: ThemeColorStyle.appBlue10,
                      size: AppDimensions.fontSize24,
                    ),
                    onTap: () => HelperUtils.navigateTo(AppRoutes.supportFAQScreen),
                  ),*/
                  /* Container(
                    margin: EdgeInsets.symmetric(horizontal: AppDimensions.width25),
                    height: 1,
                    color: ThemeColorStyle.appGray20,
                  ),*/
                  // social media
                  getSocialMediaSection(),
                  // talk to us
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
                          text: 'Talk to Us',
                          weight: FontWeight.w600,
                        ),
                      ],
                    ),
                  ),
                  ListTile(
                    contentPadding: EdgeInsets.symmetric(
                      horizontal: AppDimensions.width25,
                      vertical: AppDimensions.height8,
                    ),
                    leading: AppIcon(
                      shapeHeight: AppDimensions.width40,
                      shapeWidth: AppDimensions.width40,
                      shapeColor: ThemeColorStyle.appGray20,
                      iconData: Icons.phone_in_talk,
                      iconColor: ThemeColorStyle.appBlue,
                      iconSize: AppDimensions.fontSize24,
                      shapeRadius: AppDimensions.radius12,
                    ),
                    title: TextMedium(
                      text: getSupportPhone(),
                      size: AppDimensions.fontSize16,
                      weight: FontWeight.w400,
                    ),
                    trailing: Icon(
                      Icons.expand_circle_down_rounded,
                      color: ThemeColorStyle.appBlue10,
                      size: AppDimensions.fontSize24,
                    ),
                    onTap: () {},
                  ),
                  Container(
                    margin: EdgeInsets.symmetric(horizontal: AppDimensions.width25),
                    height: 1,
                    color: ThemeColorStyle.appGray20,
                  ),
                  ListTile(
                    contentPadding: EdgeInsets.symmetric(
                      horizontal: AppDimensions.width25,
                      vertical: AppDimensions.height8,
                    ),
                    leading: AppIcon(
                      shapeHeight: AppDimensions.width40,
                      shapeWidth: AppDimensions.width40,
                      shapeColor: ThemeColorStyle.appGray20,
                      iconData: Icons.email,
                      iconColor: ThemeColorStyle.appBlue,
                      iconSize: AppDimensions.fontSize24,
                      shapeRadius: AppDimensions.radius12,
                    ),
                    title: TextMedium(
                      text: getSupportEmail(),
                      size: AppDimensions.fontSize16,
                      weight: FontWeight.w400,
                    ),
                    trailing: Icon(
                      Icons.expand_circle_down_rounded,
                      color: ThemeColorStyle.appBlue10,
                      size: AppDimensions.fontSize24,
                    ),
                    onTap: () => {},
                  ),
                  Container(
                    margin: EdgeInsets.symmetric(horizontal: AppDimensions.width25),
                    height: 1,
                    color: ThemeColorStyle.appGray20,
                  ),

                  // we love feedback
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
                          text: 'We Love Feedback',
                          weight: FontWeight.w600,
                        ),
                      ],
                    ),
                  ),
                  ListTile(
                    contentPadding: EdgeInsets.symmetric(
                      horizontal: AppDimensions.width25,
                      vertical: AppDimensions.height8,
                    ),
                    leading: AppIcon(
                      shapeHeight: AppDimensions.width40,
                      shapeWidth: AppDimensions.width40,
                      shapeColor: ThemeColorStyle.appGray20,
                      iconData: Icons.chat,
                      iconColor: ThemeColorStyle.appBlue,
                      iconSize: AppDimensions.fontSize24,
                      shapeRadius: AppDimensions.radius12,
                    ),
                    title: TextMedium(
                      text: 'Send Feedback',
                      size: AppDimensions.fontSize16,
                      weight: FontWeight.w400,
                    ),
                    trailing: Icon(
                      Icons.expand_circle_down_rounded,
                      color: ThemeColorStyle.appBlue10,
                      size: AppDimensions.fontSize24,
                    ),
                    onTap: () => HelperUtils.navigateTo(AppRoutes.supportFeedBackScreen),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }

  String getSupportPhone() {
    HospitalController controller = Get.find<HospitalController>();
    return controller.selectedHospital.supportPhone ?? '';
  }

  String getSupportEmail() {
    HospitalController controller = Get.find<HospitalController>();
    return controller.selectedHospital.supportEmail ?? '';
  }

  Widget getSocialMediaSection() {
    return Column(
      children: const [
        /* Container(
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
                text: 'Social Media',
                weight: FontWeight.w600,
              ),
            ],
          ),
        ),
        ListTile(
          contentPadding: EdgeInsets.symmetric(
            horizontal: AppDimensions.width25,
            vertical: AppDimensions.height8,
          ),
          leading: AppIcon(
            shapeHeight: AppDimensions.width40,
            shapeWidth: AppDimensions.width40,
            shapeColor: ThemeColorStyle.appGray20,
            iconData: Icons.camera_alt_outlined,
            iconColor: ThemeColorStyle.appBlue,
            iconSize: AppDimensions.fontSize24,
            shapeRadius: AppDimensions.radius12,
          ),
          title: TextMedium(
            text: 'Instagram',
            size: AppDimensions.fontSize16,
            weight: FontWeight.w400,
          ),
          trailing: Icon(
            Icons.expand_circle_down_rounded,
            color: ThemeColorStyle.appBlue10,
            size: AppDimensions.fontSize24,
          ),
          onTap: () => {},
        ),
        Container(
          margin: EdgeInsets.symmetric(horizontal: AppDimensions.width25),
          height: 1,
          color: ThemeColorStyle.appGray20,
        ),
        ListTile(
          contentPadding: EdgeInsets.symmetric(
            horizontal: AppDimensions.width25,
            vertical: AppDimensions.height8,
          ),
          leading: Container(
            height: AppDimensions.width40,
            width: AppDimensions.width40,
            decoration: BoxDecoration(
              color: ThemeColorStyle.appGray20,
              borderRadius: BorderRadius.circular(
                AppDimensions.radius100,
              ),
            ),
            child: Padding(
              padding: EdgeInsets.symmetric(horizontal: AppDimensions.width8, vertical: AppDimensions.height5),
              child: SvgPicture.asset(
                "assets/images/twitter.svg",
                fit: BoxFit.fitWidth,
              ),
            ),
          ),
          title: TextMedium(
            text: 'Twitter',
            size: AppDimensions.fontSize16,
            weight: FontWeight.w400,
          ),
          trailing: Icon(
            Icons.expand_circle_down_rounded,
            color: ThemeColorStyle.appBlue10,
            size: AppDimensions.fontSize24,
          ),
          onTap: () => {},
        ),
        Container(
          margin: EdgeInsets.symmetric(horizontal: AppDimensions.width25),
          height: 1,
          color: ThemeColorStyle.appGray20,
        ),
        ListTile(
          contentPadding: EdgeInsets.symmetric(
            horizontal: AppDimensions.width25,
            vertical: AppDimensions.height8,
          ),
          leading: Container(
            height: AppDimensions.width40,
            width: AppDimensions.width40,
            decoration: BoxDecoration(
              color: ThemeColorStyle.appGray20,
              borderRadius: BorderRadius.circular(
                AppDimensions.radius100,
              ),
            ),
            child: Padding(
              padding: EdgeInsets.symmetric(horizontal: AppDimensions.width8, vertical: AppDimensions.height5),
              child: SvgPicture.asset(
                "assets/images/facebook.svg",
                fit: BoxFit.fitWidth,
              ),
            ),
          ),
          title: TextMedium(
            text: 'Facebook',
            size: AppDimensions.fontSize16,
            weight: FontWeight.w400,
          ),
          trailing: Icon(
            Icons.expand_circle_down_rounded,
            color: ThemeColorStyle.appBlue10,
            size: AppDimensions.fontSize24,
          ),
          onTap: () => {},
        ),*/
      ],
    );
  }
}
