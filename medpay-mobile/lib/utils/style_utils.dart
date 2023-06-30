import 'package:flutter/material.dart';
import 'package:medpay/utils/app_dimensions.dart';

abstract class ThemeTextStyle {
  static TextStyle inputFieldTextStyle = TextStyle(
    fontFamily: ThemeFontFamily.primaryFontFamily,
    fontSize: AppDimensions.fontSize13,
    fontWeight: FontWeight.w400,
    fontStyle: FontStyle.normal,
  );
}

abstract class ThemeColorStyle {
  static const Color appBlue = Color(0xFF1A87DD);
  static const Color appBlue10 = Color(0xFFD0E8F8);
  static const Color appOrange = Color(0xFFF5964E);
  static const Color appWhite = Color(0xFFFFFFFF);
  static const Color appWhite20 = Color(0xFFFEF8F1);
  static const Color appLightBlue = Color(0xFFD0E7F7);
  static const Color appGray50 = Color(0xFF858585);
  static const Color appGray20 = Color(0xFFF5F5F5);
  static const Color appGray10 = Color(0xFFCECECE);
  static const Color appBlack = Color(0xFF000000);
  static const Color appRed = Color(0xFFED6557);
  static const Color appLightGreen = Color(0xFF30D6B0);
  static MaterialColor appBlue100 = Colors.blue;
  static Color appGray30 = Colors.grey.shade400;
}

abstract class ThemeFontFamily {
  static const String primaryFontFamily = "Lato";
  static const String secondaryFontFamily = "Poppins";
}

abstract class ThemeInputStyle {
  static InputDecoration primaryCodeVerifyInputDecoration = InputDecoration(
    contentPadding: const EdgeInsets.only(left: 0, bottom: 0, top: 0),
    border: OutlineInputBorder(borderRadius: BorderRadius.all(Radius.circular(AppDimensions.radius7))),
    focusedBorder: const OutlineInputBorder(
      borderSide: BorderSide(
        color: ThemeColorStyle.appBlue,
        width: 1.0,
      ),
      borderRadius: BorderRadius.all(Radius.circular(7)),
    ),
  );

  // using dimensions
  static ButtonStyle primaryButtonStyle = ElevatedButton.styleFrom(
    backgroundColor: ThemeColorStyle.appOrange,
    fixedSize: Size(AppDimensions.width273, AppDimensions.height40),
    shape: RoundedRectangleBorder(
      borderRadius: BorderRadius.circular(AppDimensions.radius12),
    ),
  );

  static InputDecoration dropDownFieldStyle = InputDecoration(
    enabledBorder: OutlineInputBorder(
      borderSide: const BorderSide(color: ThemeColorStyle.appGray50, width: 1),
      borderRadius: BorderRadius.circular(AppDimensions.radius7),
    ),
    border: OutlineInputBorder(
      borderSide: const BorderSide(color: ThemeColorStyle.appBlack, width: 1),
      borderRadius: BorderRadius.circular(AppDimensions.radius7),
    ),
    filled: false,
    fillColor: Colors.white,
  );

  static ButtonStyle primaryIconButtonStyle = ElevatedButton.styleFrom(
    backgroundColor: ThemeColorStyle.appOrange,
    fixedSize: Size(AppDimensions.width50, AppDimensions.height45),
    shape: RoundedRectangleBorder(
      borderRadius: BorderRadius.circular(AppDimensions.radius12),
    ),
  );

  static ButtonStyle primaryTextButtonStyle = TextButton.styleFrom(
    padding: EdgeInsets.zero,
    minimumSize: Size(AppDimensions.width50, AppDimensions.height30),
    tapTargetSize: MaterialTapTargetSize.shrinkWrap,
  );

  static ButtonStyle primaryModalButtonStyle = TextButton.styleFrom(
    foregroundColor: ThemeColorStyle.appOrange,
    backgroundColor: ThemeColorStyle.appOrange,
    padding: EdgeInsets.symmetric(horizontal: AppDimensions.width10),
    tapTargetSize: MaterialTapTargetSize.shrinkWrap,
    shape: RoundedRectangleBorder(
      borderRadius: BorderRadius.circular(AppDimensions.radius7),
    ),
  );

  static TextStyle searchInputStyle = TextStyle(
    fontFamily: ThemeFontFamily.primaryFontFamily,
    fontSize: AppDimensions.fontSize15,
    fontWeight: FontWeight.w400,
    fontStyle: FontStyle.normal,
  );

  static InputDecoration getSearchInputDecoration(String hint, {bool showClearIcon = false, VoidCallback? action}) {
    return InputDecoration(
      suffixIcon: showClearIcon
          ? GestureDetector(
              onTap: action,
              child: Icon(Icons.clear, size: AppDimensions.fontSize18),
            )
          : null,
      contentPadding: EdgeInsets.all(AppDimensions.width20),
      border: OutlineInputBorder(
        borderRadius: BorderRadius.all(
          Radius.circular(AppDimensions.radius7),
        ),
      ),
      enabledBorder: OutlineInputBorder(
        borderRadius: BorderRadius.all(Radius.circular(AppDimensions.radius7)),
        borderSide: const BorderSide(color: ThemeColorStyle.appGray10),
      ),
      hintText: hint,
      hintStyle: TextStyle(
        fontSize: AppDimensions.fontSize14,
        fontStyle: FontStyle.normal,
        fontFamily: ThemeFontFamily.primaryFontFamily,
        fontWeight: FontWeight.w500,
        color: ThemeColorStyle.appBlack,
      ),
    );
  }
}
