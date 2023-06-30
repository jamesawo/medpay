import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:medpay/controllers/auth_controller.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/app_routes.dart';
import 'package:medpay/utils/helper_utils.dart';
import 'package:medpay/utils/style_utils.dart';
import 'package:medpay/widgets/app_icon.dart';
import 'package:medpay/widgets/app_top_sliver_title.dart';
import 'package:medpay/widgets/hero_account.dart';
import 'package:medpay/widgets/text_medium.dart';
import 'package:medpay/widgets/text_small.dart';

class AccountHomeScreen extends StatefulWidget {
  const AccountHomeScreen({Key? key}) : super(key: key);

  @override
  State<AccountHomeScreen> createState() => _AccountHomeScreenState();
}

class _AccountHomeScreenState extends State<AccountHomeScreen> {
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
    setState(() => showTitle = value);
  }

  @override
  void dispose() {
    scrollController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return GetBuilder<AuthController>(builder: (controller) {
      return Scaffold(
        body: CustomScrollView(
          controller: scrollController,
          slivers: [
            SliverAppBar(
              titleSpacing: 0,
              title: AnimatedOpacity(
                duration: const Duration(milliseconds: 200),
                opacity: showTitle ? 1 : 0,
                child: const AppTopSliverTitle(title: 'My Account'),
              ),
              backgroundColor: ThemeColorStyle.appBlue,
              automaticallyImplyLeading: false,
              expandedHeight: AppDimensions.height240,
              flexibleSpace: FlexibleSpaceBar(
                background: HeroAccount(
                  name: '${controller.loggedInUser.name}',
                  email: '${controller.loggedInUser.username}',
                ),
              ),
              pinned: true,
              floating: true,
              stretch: false,
              collapsedHeight: AppDimensions.height50,
              toolbarHeight: AppDimensions.height50,
            ),
            SliverToBoxAdapter(
              child: Column(
                children: [
                  SizedBox(height: AppDimensions.height5),
                  Divider(
                    color: ThemeColorStyle.appGray20,
                    thickness: AppDimensions.height20,
                    height: AppDimensions.height30,
                  ),
                ],
              ),
            ),
            SliverList(
              delegate: SliverChildListDelegate(
                [
                  // todo:: add the commented component when the features are ready
                  /* ListTile(
                    contentPadding: EdgeInsets.only(left: AppDimensions.width25, right: AppDimensions.width25),
                    leading: AppIcon(
                      shapeHeight: AppDimensions.width40,
                      shapeWidth: AppDimensions.width40,
                      shapeColor: ThemeColorStyle.appGray20,
                      iconData: Icons.manage_accounts,
                      iconColor: ThemeColorStyle.appBlue,
                      iconSize: AppDimensions.fontSize24,
                      shapeRadius: AppDimensions.radius12,
                    ),
                    title: TextMedium(
                      text: 'Manage Profile',
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
                    onTap: () => print('Manage Profile Tapped'),
                  ),
                  Container(
                    margin: EdgeInsets.symmetric(horizontal: AppDimensions.width25),
                    height: 1,
                    color: ThemeColorStyle.appGray20,
                  ),*/
                  /*  ListTile(
                    contentPadding: EdgeInsets.only(left: AppDimensions.width25, right: AppDimensions.width25),
                    leading: AppIcon(
                      shapeHeight: AppDimensions.width40,
                      shapeWidth: AppDimensions.width40,
                      shapeColor: ThemeColorStyle.appGray20,
                      iconData: Icons.dashboard_rounded,
                      iconColor: ThemeColorStyle.appBlue,
                      iconSize: AppDimensions.fontSize24,
                      shapeRadius: AppDimensions.radius12,
                    ),
                    title: TextMedium(
                      text: 'View Dashboard',
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
                    onTap: () {},
                  ),*/
                  /*  Container(
                    margin: EdgeInsets.symmetric(horizontal: AppDimensions.width25),
                    height: 1,
                    color: ThemeColorStyle.appGray20,
                  ),*/
                  ListTile(
                    contentPadding: EdgeInsets.only(left: AppDimensions.width25, right: AppDimensions.width25),
                    leading: AppIcon(
                      shapeHeight: AppDimensions.width40,
                      shapeWidth: AppDimensions.width40,
                      shapeColor: ThemeColorStyle.appGray20,
                      iconData: Icons.lock,
                      iconColor: ThemeColorStyle.appBlue,
                      iconSize: AppDimensions.fontSize24,
                      shapeRadius: AppDimensions.radius12,
                    ),
                    title: TextMedium(
                      text: 'Change Password',
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
                    onTap: () => HelperUtils.navigateTo(AppRoutes.authChangePasswordScreen),
                  ),
                  Container(
                    margin: EdgeInsets.symmetric(horizontal: AppDimensions.width25),
                    height: 1,
                    color: ThemeColorStyle.appGray20,
                  ),
                  ListTile(
                    contentPadding: EdgeInsets.only(left: AppDimensions.width25, right: AppDimensions.width25),
                    leading: AppIcon(
                      shapeHeight: AppDimensions.width40,
                      shapeWidth: AppDimensions.width40,
                      shapeColor: ThemeColorStyle.appGray20,
                      iconData: Icons.medical_services,
                      iconColor: ThemeColorStyle.appBlue,
                      iconSize: AppDimensions.fontSize24,
                      shapeRadius: AppDimensions.radius12,
                    ),
                    title: TextMedium(
                      text: 'Hospital Settings',
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
                    onTap: () => HelperUtils.navigateToWithArgs(AppRoutes.homeSetLocationScreen, false),
                  ),
                  /*Container(
                    margin: EdgeInsets.symmetric(horizontal: AppDimensions.width25),
                    height: 1,
                    color: ThemeColorStyle.appGray20,
                  ),*/
                  /*  ListTile(
                    contentPadding: EdgeInsets.only(left: AppDimensions.width25, right: AppDimensions.width25),
                    leading: AppIcon(
                      shapeHeight: AppDimensions.width40,
                      shapeWidth: AppDimensions.width40,
                      shapeColor: ThemeColorStyle.appGray20,
                      iconData: Icons.settings_rounded,
                      iconColor: ThemeColorStyle.appBlue,
                      iconSize: AppDimensions.fontSize24,
                      shapeRadius: AppDimensions.radius12,
                    ),
                    title: TextMedium(
                      text: 'Settings',
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
                    onTap: () => HelperUtils.navigateTo(AppRoutes.settingHomeScreen),
                  ),*/
                  Container(
                    margin: EdgeInsets.symmetric(horizontal: AppDimensions.width25),
                    height: 1,
                    color: ThemeColorStyle.appGray20,
                  ),
                  ListTile(
                    contentPadding: EdgeInsets.only(left: AppDimensions.width25, right: AppDimensions.width25),
                    leading: AppIcon(
                      shapeHeight: AppDimensions.width40,
                      shapeWidth: AppDimensions.width40,
                      shapeColor: ThemeColorStyle.appGray20,
                      iconData: Icons.logout,
                      iconColor: ThemeColorStyle.appBlue,
                      iconSize: AppDimensions.fontSize24,
                      shapeRadius: AppDimensions.radius12,
                    ),
                    title: TextMedium(
                      text: 'Logout',
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
                    onTap: () {
                      getAlertConfirmDialog(controller);
                    },
                  ),
                ],
              ),
            ),
          ],
        ),
        // bottomNavigationBar: const AppBottomNavBar(activePage: 0),
      );
    });
  }

  Future<void> getAlertConfirmDialog(AuthController controller) {
    return showDialog<void>(
      context: context,
      barrierDismissible: false, // user must tap button!
      builder: (BuildContext context) {
        return AlertDialog(
          title: const TextSmall(text: 'Logout!'),
          content: SingleChildScrollView(
            child: ListBody(
              children: <Widget>[
                TextSmall(
                  text: 'Are you sure you want to logout?',
                  size: AppDimensions.fontSize14,
                ),
              ],
            ),
          ),
          actions: <Widget>[
            TextButton(
              child: const TextSmall(text: 'Cancel', color: ThemeColorStyle.appRed),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
            TextButton(
              child: const TextSmall(text: 'Yes, Logout', color: ThemeColorStyle.appBlue),
              onPressed: () => controller.doLogout(),
            )
          ],
        );
      },
    );
  }
}
