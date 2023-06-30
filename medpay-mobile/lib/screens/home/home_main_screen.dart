import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:medpay/screens/account/account_home_screen.dart';
import 'package:medpay/screens/home/home_dashboard_screen.dart';
import 'package:medpay/screens/support/support_home_screen.dart';
import 'package:medpay/utils/style_utils.dart';

class HomeMainScreen extends StatefulWidget {
  const HomeMainScreen({Key? key}) : super(key: key);

  @override
  State<HomeMainScreen> createState() => _HomeMainScreenState();
}

class _HomeMainScreenState extends State<HomeMainScreen> {
  int _selectedIndex = 1;

  List pages = const [
    AccountHomeScreen(),
    HomeDashboardScreen(),
    SupportHomeScreen(),
  ];

  Future<void> onRefreshPage() async {}

  @override
  Widget build(BuildContext context) {
    return AnnotatedRegion<SystemUiOverlayStyle>(
      value: SystemUiOverlayStyle.light,
      child: RefreshIndicator(
        onRefresh: () => onRefreshPage(),
        child: Scaffold(
          resizeToAvoidBottomInset: true,
          backgroundColor: ThemeColorStyle.appWhite,
          body: pages[_selectedIndex],
          bottomNavigationBar: getBottomNavigation(),
        ),
      ),
    );
  }

  NavigationBar getBottomNavigation() {
    return NavigationBar(
      backgroundColor: Colors.transparent.withOpacity(0.0),
      onDestinationSelected: (int index) {
        setState(() => _selectedIndex = index);
      },
      animationDuration: const Duration(seconds: 3),
      selectedIndex: _selectedIndex,
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
