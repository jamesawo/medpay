import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:medpay/controllers/auth_controller.dart';
import 'package:medpay/data/constants/app_constants.dart';
import 'package:medpay/data/models/bill_model.dart';
import 'package:medpay/data/models/hospital_service.dart';
import 'package:medpay/screens/account/account_home_screen.dart';
import 'package:medpay/screens/auth/auth_change_password_screen.dart';
import 'package:medpay/screens/auth/auth_forgot_password_screen.dart';
import 'package:medpay/screens/auth/auth_login_screen.dart';
import 'package:medpay/screens/auth/auth_register_screen.dart';
import 'package:medpay/screens/auth/auth_reset_password_screen.dart';
import 'package:medpay/screens/auth/auth_update_email_screen.dart';
import 'package:medpay/screens/auth/auth_verify_email_screen.dart';
import 'package:medpay/screens/home/home_dashboard_screen.dart';
import 'package:medpay/screens/home/home_main_screen.dart';
import 'package:medpay/screens/home/home_set_location_screen.dart';
import 'package:medpay/screens/onboarding/onboarding_screen.dart';
import 'package:medpay/screens/payment/payment_add_service_items_screen.dart';
import 'package:medpay/screens/payment/payment_bill_detail_screen.dart';
import 'package:medpay/screens/payment/payment_bill_search_screen.dart';
import 'package:medpay/screens/payment/payment_credit_card_screen.dart';
import 'package:medpay/screens/payment/payment_payer_detail_screen.dart';
import 'package:medpay/screens/payment/payment_status_screen.dart';
import 'package:medpay/screens/payment/payment_verification_screen.dart';
import 'package:medpay/screens/setting/setting_about_screen.dart';
import 'package:medpay/screens/setting/setting_home_screen.dart';
import 'package:medpay/screens/setting/setting_privary_policy_screen.dart';
import 'package:medpay/screens/setting/setting_terms_of_service_screen.dart';
import 'package:medpay/screens/splash/splash_screen.dart';
import 'package:medpay/screens/support/support_faq_screen.dart';
import 'package:medpay/screens/support/support_home_screen.dart';
import 'package:medpay/screens/support/support_send_feedback_screen.dart';
import 'package:medpay/screens/transaction/transaction_history_screen.dart';
import 'package:medpay/screens/transaction/transaction_statement_screen.dart';
import 'package:medpay/utils/app_shared_preference.dart';

class AppRoutes {
  // Auth
  static const String previewScreen = 'preview';
  static const String splashScreen = 'splash';
  static const String onboardingScreen = 'onboarding';
  static const String authLoginScreen = 'login';
  static const String authRegisterScreen = 'register';
  static const String authForgotPasswordScreen = 'forgot-password';
  static const String authResetPasswordScreen = 'reset-password';
  static const String authVerifyEmailScreen = 'verify-email';
  static const String authUpdateEmailScreen = 'update-email';
  static const String authChangePasswordScreen = 'change-password';

  // home
  static const String homeMainScreen = 'home-main';
  static const String homeSetLocationScreen = 'home-set-location';
  static const String homeDashboardScreen = 'home-dashboard';

  // payment
  static const String paymentBillDetailScreen = 'payment-bill-detail';
  static const String paymentBillSearchScreen = 'payment-bill-search-screen';
  static const String paymentAddServiceItemsScreen = 'payment-add-service-items-screen';
  static const String paymentCardScreen = 'payment-card-screen';
  static const String paymentStatusScreen = 'payment-confirm-screen';
  static const String paymentPayerDetailScreen = 'payment-payer-detail-screen';
  static const String paymentVerificationScreen = 'payment-verification-screen';

  //account
  static const String accountHomeScreen = 'account-home-screen';

  // settings
  static const String settingHomeScreen = 'setting-home-screen';
  static const String settingAboutScreen = 'setting-about-screen';
  static const String settingTermsOfServiceScreen = 'setting-tos-screen';
  static const String settingPrivacyPolicyScreen = 'setting-pp-screen';

  // support
  static const String supportHomeScreen = 'support-home-screen';
  static const String supportFAQScreen = 'support-faq-screen';
  static const String supportFeedBackScreen = 'support-feedback-screen';

  // transaction
  static const String transactionHistoryScreen = 'transaction-history-screen';
  static const String transactionStatementScreen = 'transaction-statement-screen';

  static Route<dynamic> controller(RouteSettings settings) {
    switch (settings.name) {
      //onboarding
      case onboardingScreen:
        return MaterialPageRoute(builder: (context) => const OnBoardingScreen());
      case splashScreen:
        return MaterialPageRoute(builder: (context) => const SplashScreen());
      //auth
      case authLoginScreen:
        bool isLoggedIn = Get.find<AuthController>().isUserLoggedIn();
        if (isLoggedIn == true) {
          return MaterialPageRoute(builder: (context) => const HomeMainScreen());
        }
        return MaterialPageRoute(builder: (context) => const AuthLoginScreen());
      case authRegisterScreen:
        return MaterialPageRoute(builder: (context) => const AuthRegisterScreen());
      case authForgotPasswordScreen:
        return MaterialPageRoute(builder: (context) => const AuthForgotPasswordScreen());
      case authResetPasswordScreen:
        return MaterialPageRoute(builder: (context) => const AuthResetPasswordScreen());
      case authVerifyEmailScreen:
        return MaterialPageRoute(builder: (context) => const AuthVerifyEmailScreen());
      case authUpdateEmailScreen:
        return MaterialPageRoute(builder: (context) => const AuthUpdateEmailScreen());
      case authChangePasswordScreen:
        return MaterialPageRoute(builder: (context) => const AuthChangePasswordScreen());
      // home
      case homeSetLocationScreen:
        final isShowTitle = settings.arguments as bool;
        return MaterialPageRoute(
          builder: (context) => HomeSetLocationScreen(showTitle: isShowTitle),
        );
      case homeDashboardScreen:
        // check if user hospital is saved in shared preference
        var hospitalUid = Get.find<AppSharedPreference>().getValue(AppConstants.keyHospitalId);
        if (hospitalUid == null) {
          // if not send user to set location screen
          return MaterialPageRoute(
            builder: (context) => const HomeSetLocationScreen(showTitle: true),
          );
        }
        return MaterialPageRoute(builder: (context) => const HomeDashboardScreen());

      case homeMainScreen:
        var hospitalUid = Get.find<AppSharedPreference>().getValue(AppConstants.keyHospitalId);
        if (hospitalUid == null) {
          return MaterialPageRoute(builder: (context) => const HomeSetLocationScreen(showTitle: true));
        }
        return MaterialPageRoute(builder: (context) => const HomeMainScreen());

      // payment
      case paymentBillDetailScreen:
        final bill = settings.arguments as HospitalBill;
        return MaterialPageRoute(builder: (context) => PaymentBillDetailScreen(bill: bill));
      case paymentBillSearchScreen:
        var search = settings.arguments as String?;
        return MaterialPageRoute(builder: (context) => PaymentBillSearchScreen(searchTerm: search));
      case paymentAddServiceItemsScreen:
        var search = settings.arguments as HospitalService?;
        return MaterialPageRoute(builder: (context) => PaymentAddServiceItemsScreen(service: search));
      case paymentCardScreen:
        return MaterialPageRoute(builder: (context) => const PaymentCreditCardScreen());
      case paymentPayerDetailScreen:
        return MaterialPageRoute(builder: (context) => const PaymentPayerDetailScreen());
      case paymentStatusScreen:
        final data = settings.arguments as Map<String, dynamic>;
        return MaterialPageRoute(
          builder: (context) => PaymentStatusScreen(transaction: data['transaction'], buttonText: data['buttonText']),
        );
      case paymentVerificationScreen:
        return MaterialPageRoute(builder: (context) => const PaymentVerificationScreen());
      //account
      case accountHomeScreen:
        return MaterialPageRoute(builder: (context) => const AccountHomeScreen());
      // setting
      case settingHomeScreen:
        return MaterialPageRoute(builder: (context) => const SettingHomeScreen());
      case settingTermsOfServiceScreen:
        return MaterialPageRoute(builder: (context) => const SettingTermsOfServiceScreen());
      case settingAboutScreen:
        return MaterialPageRoute(builder: (context) => const SettingAboutScreen());
      case settingPrivacyPolicyScreen:
        return MaterialPageRoute(builder: (context) => const SettingPrivacyScreen());
      // support
      case supportHomeScreen:
        return MaterialPageRoute(builder: (context) => const SupportHomeScreen());
      case supportFAQScreen:
        return MaterialPageRoute(builder: (context) => const SupportFaqScreen());
      case supportFeedBackScreen:
        return MaterialPageRoute(builder: (context) => const SupportSendFeedBackScreen());
      // transaction
      case transactionHistoryScreen:
        return MaterialPageRoute(builder: (context) => const TransactionHistoryScreen());
      case transactionStatementScreen:
        return MaterialPageRoute(builder: (context) => const TransactionStatementScreen());

      default:
        throw ('this route name does not exist');
    }
  }
}
