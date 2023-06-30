import 'package:get/get.dart';
import 'package:intl/date_symbol_data_local.dart';
import 'package:intl/intl.dart';
import 'package:medpay/controllers/auth_controller.dart';
import 'package:medpay/controllers/hospital_controller.dart';
import 'package:medpay/controllers/payment_controller.dart';
import 'package:medpay/controllers/recent_transaction_controller.dart';
import 'package:medpay/controllers/user_controller.dart';
import 'package:medpay/data/api/api_client.dart';
import 'package:medpay/data/api/base_provider.dart';
import 'package:medpay/data/constants/app_constants.dart';
import 'package:medpay/repositories/auth_repository.dart';
import 'package:medpay/repositories/hospital_repository.dart';
import 'package:medpay/repositories/payment_repository.dart';
import 'package:medpay/repositories/recent_transaction_repository.dart';
import 'package:medpay/repositories/user_repository.dart';
import 'package:shared_preferences/shared_preferences.dart';

import 'app_shared_preference.dart';

class Dependency {
  static Future<void> init() async {
    // get sharedPreference instance
    final SharedPreferences sharedPreferences = await SharedPreferences.getInstance();

    // shared preference
    Get.lazyPut(() => AppSharedPreference(sharedPreferences: sharedPreferences));
    Get.lazyPut(() => BaseProvider());

    // api client
    Get.lazyPut(() => ApiClient());
    // repositories
    Get.lazyPut(() => HospitalRepository(apiClient: Get.find()));
    Get.lazyPut(() => AuthRepository(apiClient: Get.find()));
    Get.lazyPut(() => UserRepository(apiClient: Get.find()));
    Get.lazyPut(() => PaymentRepository(apiClient: Get.find()));
    Get.lazyPut(() => RecentTransactionRepository(apiClient: Get.find()));
    // controllers
    Get.lazyPut(() => HospitalController(hospitalRepository: Get.find()));
    Get.lazyPut(() => UserController(userRepository: Get.find()));
    Get.lazyPut(() => AuthController(authRepository: Get.find()));
    Get.lazyPut(() => PaymentController(paymentRepository: Get.find()));
    Get.lazyPut(() => RecentTransactionController(repository: Get.find()));
  }

  static Future<void> load() async {
    /// load initial app data from api on startup.
    initializeDateFormatting();
    Intl.defaultLocale = AppConstants.appLocale;
    var hospitalController = Get.find<HospitalController>();
    hospitalController.getSavedHospitalFromLocalStorage();
    hospitalController.getHospitalsFromApi();
    Get.find<RecentTransactionController>().getRecentTransactionsFromApi();
  }
}
