import 'package:flutter/material.dart';
import 'package:medpay/utils/app_routes.dart';
import 'package:medpay/utils/helper_utils.dart';
import 'package:medpay/utils/style_utils.dart';

class AppBottomNavBar extends StatefulWidget {
  final int? activePage;

  const AppBottomNavBar({this.activePage = 0, Key? key}) : super(key: key);

  @override
  State<AppBottomNavBar> createState() => _AppBottomNavBarState();
}

class _AppBottomNavBarState extends State<AppBottomNavBar> {
  late int currentIndex;

  @override
  void initState() {
    super.initState();
    currentIndex = widget.activePage!;
  }

  @override
  Widget build(BuildContext context) {
    return NavigationBar(
      backgroundColor: Colors.transparent.withOpacity(0.0),
      onDestinationSelected: (int index) {
        setState(() {
          currentIndex = index;
          switch (currentIndex) {
            case 0:
              HelperUtils.popBeforeNavigateTo(AppRoutes.accountHomeScreen);
              break;
            case 1:
              HelperUtils.popBeforeNavigateTo(AppRoutes.homeDashboardScreen);
              break;
            case 2:
              HelperUtils.popBeforeNavigateTo(AppRoutes.supportHomeScreen);
              break;
            default:
              break;
          }
        });
      },
      selectedIndex: currentIndex,
      destinations: const [
        NavigationDestination(
          icon: Icon(Icons.person_outline),
          selectedIcon: Icon(Icons.person, color: ThemeColorStyle.appBlue),
          label: 'My Account',
        ),
        NavigationDestination(
          icon: Icon(Icons.home_outlined),
          selectedIcon: Icon(Icons.home, color: ThemeColorStyle.appBlue),
          label: 'Home',
        ),
        NavigationDestination(
          icon: Icon(Icons.headset_mic_outlined),
          selectedIcon: Icon(Icons.headset_mic, color: ThemeColorStyle.appBlue),
          label: 'Support',
        ),
      ],
    );
  }
}
