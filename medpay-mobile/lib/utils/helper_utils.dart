import 'dart:convert';
import 'dart:io';

import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:intl/date_symbol_data_local.dart';
import 'package:intl/intl.dart';
import 'package:medpay/data/constants/app_constants.dart';
import 'package:medpay/data/constants/exception_constants.dart';
import 'package:medpay/data/enums/app_enum.dart';
import 'package:medpay/data/payload/response.payload.dart';
import 'package:medpay/screens/home/home_main_screen.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/logger_utils.dart';
import 'package:medpay/utils/style_utils.dart';
import 'package:medpay/widgets/text_small.dart';

class HelperUtils {
  static BuildContext appContext = Get.context!;

  static void popUntil(String screen) {
    Navigator.of(appContext, rootNavigator: true).popUntil(ModalRoute.withName(screen));
  }

  static void popOnly() {
    Navigator.of(appContext).pop(appContext);
  }

  static void popBeforeNavigateTo(String screen) {
    Navigator.popAndPushNamed(appContext, screen);
  }

  static void popBeforeNavigateToWithArgs(String screen, dynamic args) {
    Navigator.popAndPushNamed(appContext, screen, arguments: args);
  }

  static void replaceWith(String screen) {
    Navigator.pushReplacementNamed(appContext, screen);
  }

  static void navigateToAndRemoveAll(String screen) {
    Navigator.of(appContext).pushNamedAndRemoveUntil(screen, (Route<dynamic> route) => false, arguments: {});
  }

  static void navigateToMainAndRemoveAll() {
    Navigator.pushAndRemoveUntil(appContext, MaterialPageRoute(builder: (BuildContext context) {
      return const HomeMainScreen();
    }), (r) {
      return false;
    });
  }

  /// navigate to route
  static void navigateTo(String screen) {
    Navigator.pushNamed(appContext, screen);
  }

  /// navigates to route with args
  static void navigateToWithArgs(String screen, dynamic args) {
    Navigator.pushNamed(appContext, screen, arguments: args);
  }

  /// simple password validator
  /// checks if password value is empty or null
  /// checks if password characters is less than 6
  static String? onValidatePassword(String? password) {
    if (password == null || password.isEmpty) {
      return "Password cannot be empty";
    } else if (password.characters.length < 2) {
      return "Password is too short";
    }
    return null;
    /*
    static password(String? txt) {
    if (txt == null || txt.isEmpty) {
      return "Invalid password!";
    }
    if (txt.length < 8) {
      return "Password must has 8 characters";
    }
    if (!txt.contains(RegExp(r'[A-Z]'))) {
      return "Password must has uppercase";
    }
    if (!txt.contains(RegExp(r'[0-9]'))) {
      return "Password must has digits";
    }
    if (!txt.contains(RegExp(r'[a-z]'))) {
      return "Password must has lowercase";
    }
    if (!txt.contains(RegExp(r'[#?!@$%^&*-]'))) {
      return "Password must has special characters";
    } else
      return;
     */
  }

  /// simple validator, checks if email is empty
  /// uses GET Utils.isEmail method to check if value is a valid email address
  static String? onValidateEmail(String? email) {
    if (email == null || email.isEmpty) {
      return "Email cannot be empty";
    } else if (!GetUtils.isEmail(email)) {
      return "Email format is incorrect";
    }
    return null;
  }

  /// simple text validator to check if text is empty
  /// @param text which is the value to validate
  /// @param name which is the name of the label associated with input field, used to display error message
  /// eg: if text value is empty and name equals `username`
  /// error message should be `username cannot be empty`
  static String? onValidateText(String? text, String name) {
    if (text == null || text.isEmpty || text.characters.isEmpty) {
      return "$name cannot be empty";
    }
    return null;
  }

  /// get currency symbol
  static String? get currencySymbol {
    //try this on android &#x20A6;
    var numberFormat = NumberFormat.simpleCurrency(locale: Platform.localeName, name: 'NGN');
    return numberFormat.currencySymbol;
  }

  /// get number format with currency symbol
  static NumberFormat get numberFormat => NumberFormat.simpleCurrency(
        locale: Platform.localeName,
        name: 'NGN',
      );

  /// get number format without currency symbol
  static NumberFormat get numberFormatNoSymbol => NumberFormat.currency(
        locale: Platform.localeName,
        name: 'NGN',
        symbol: '',
      );

  /// when called, shows a snack bar
  /// @param SuccessOrErrorEnum will determine what background color is applied on snack bar
  /// @param message: message of the snack bar
  /// @param title: title of the snack bar
  static void showCustomSnackBar({
    required String message,
    required SuccessOrErrorEnum type,
    required String title,
  }) {
    Get.snackbar(
      title,
      message,
      titleText: TextSmall(text: title, color: ThemeColorStyle.appWhite),
      messageText: TextSmall(text: message, color: ThemeColorStyle.appWhite),
      snackPosition: SnackPosition.TOP,
      colorText: ThemeColorStyle.appWhite,
      backgroundColor: type == SuccessOrErrorEnum.success ? ThemeColorStyle.appLightGreen : ThemeColorStyle.appRed,
    );
  }

  /// when called, shows an error snack bar notification
  /// @optional @param message: defaults to the constant value
  /// @optional @param title: the title of the snack bar, defaults to the constant value.
  /// @return void
  ///
  static void showErrorSnackBar({
    String? message = ExceptionConstants.errorDefaultMessage,
    String? title = ExceptionConstants.errorDefaultTitle,
  }) {
    var barTitle = title ?? ExceptionConstants.errorDefaultTitle;
    var barMessage = message ?? ExceptionConstants.errorDefaultMessage;
    Get.snackbar(
      barTitle,
      barMessage,
      // message,
      titleText: TextSmall(
        size: AppDimensions.fontSize15,
        text: barTitle,
        color: ThemeColorStyle.appWhite,
        weight: FontWeight.w600,
      ),
      messageText: TextSmall(
        size: AppDimensions.fontSize13,
        text: barMessage,
        color: ThemeColorStyle.appWhite,
        weight: FontWeight.w600,
      ),

      snackPosition: SnackPosition.TOP,
      colorText: ThemeColorStyle.appWhite,
      duration: const Duration(seconds: 5),
      backgroundColor: ThemeColorStyle.appRed,
    );
  }

  static void showSuccessSnackBar({
    required String message,
    required String title,
    int duration = 5,
  }) {
    Get.snackbar(
      title,
      message,
      titleText: TextSmall(
        size: AppDimensions.fontSize15,
        text: title,
        color: ThemeColorStyle.appWhite,
        weight: FontWeight.w600,
      ),
      messageText: TextSmall(
        size: AppDimensions.fontSize14,
        text: message,
        color: ThemeColorStyle.appWhite,
        weight: FontWeight.w600,
      ),
      snackPosition: SnackPosition.TOP,
      colorText: ThemeColorStyle.appWhite,
      duration: Duration(seconds: duration),
      backgroundColor: ThemeColorStyle.appLightGreen,
    );
  }

  /// Show error from response object, log error
  ///
  /// Accepts [response], [caller]
  static void showErrorAndLog(Response response, String caller) {
    ResponsePayload payload = ResponsePayload.fromJson(jsonDecode(jsonEncode(response.body)));
    showErrorSnackBar(message: payload.error);
    LoggerUtils.logError(error: payload.error, caller: caller);
  }

  /// Formats date.
  ///
  /// Accepts optional int [year], int [month], int [day]
  static String formatDate({int? year, int? month, int? day}) {
    late DateTime now;
    initializeDateFormatting(AppConstants.appLocale, null);
    if (year != null && month != null && day != null) {
      now = DateTime(year, month, day);
    } else {
      now = DateTime.now();
    }
    return DateFormat.yMMMMd().format(now);
  }

  static Widget getSpinnerWidget([double? verticalMargin]) {
    return Container(
      margin: EdgeInsets.symmetric(
        vertical: verticalMargin ?? AppDimensions.height100,
      ),
      child: const CircularProgressIndicator(),
    );
  }
}
