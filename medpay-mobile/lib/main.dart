import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:get/get.dart';
import 'package:medpay/data/constants/app_constants.dart';
import 'package:medpay/screens/splash/splash_screen.dart';
import 'package:medpay/utils/app_routes.dart';
import 'package:medpay/utils/dependencies.dart';
import 'package:medpay/utils/style_utils.dart';

Future<void> main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await Dependency.init();
  await Dependency.load();
  // prevent landscape orientation
  SystemChrome.setPreferredOrientations([DeviceOrientation.portraitUp, DeviceOrientation.portraitDown]);
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      behavior: HitTestBehavior.translucent,
      onTap: () => FocusManager.instance.primaryFocus?.unfocus(),
      child: GetMaterialApp(
        debugShowCheckedModeBanner: false,
        title: AppConstants.appName,
        theme: ThemeData(
          splashColor: ThemeColorStyle.appBlue,
          fontFamily: ThemeFontFamily.primaryFontFamily,
          primarySwatch: ThemeColorStyle.appBlue100,
          navigationBarTheme: NavigationBarThemeData(
            labelTextStyle: MaterialStateProperty.all(
              const TextStyle(fontFamily: ThemeFontFamily.primaryFontFamily, fontSize: 14),
            ),
          ),
        ),
        home: const SplashScreen(),
        onGenerateRoute: AppRoutes.controller,
        initialRoute: AppRoutes.splashScreen,
      ),
    );
  }
}
