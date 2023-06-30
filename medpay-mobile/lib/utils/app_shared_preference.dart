import 'package:medpay/data/constants/app_constants.dart';
import 'package:medpay/data/constants/exception_constants.dart';
import 'package:medpay/utils/helper_utils.dart';
import 'package:shared_preferences/shared_preferences.dart';

class AppSharedPreference {
  AppSharedPreference({required this.sharedPreferences});
  SharedPreferences sharedPreferences;

  void storeValue(String key, String value) {
    sharedPreferences.setString(key, value);
  }

  void storeValues(String key, List<String> values) {
    sharedPreferences.setStringList(key, values);
  }

  String? getValue(String key) {
    return sharedPreferences.getString(key);
  }

  String getHospitalUuid() {
    var hospitalUuid = getValue(AppConstants.keyHospitalId);
    if (hospitalUuid == null || hospitalUuid.isEmpty) {
      HelperUtils.showErrorSnackBar(
        title: ExceptionConstants.errorSelectHospital,
        message: ExceptionConstants.errorSelectHospital,
      );
      // todo:: log user out
    }
    return hospitalUuid ?? '';
  }

  String getUserUuid() {
    return getValue(AppConstants.keyId) ?? '';
  }

  Future<bool> removeValue(String key) async {
    return await sharedPreferences.remove(key);
  }

  Future<bool> removeAll() async {
    return await sharedPreferences.clear();
  }

  Future<bool> removeAllUserSpecific() async {
    await sharedPreferences.remove(AppConstants.keyAuthToken);
    await sharedPreferences.remove(AppConstants.keyPassword);
    await sharedPreferences.remove(AppConstants.keyUsername);
    await sharedPreferences.remove(AppConstants.keyId);
    return Future.value(true);
  }
}
