import 'package:get/get.dart';
import 'package:medpay/data/api/api_client.dart';
import 'package:medpay/data/constants/app_constants.dart';
import 'package:medpay/data/constants/route_constants.dart';
import 'package:medpay/data/enums/app_enum.dart';
import 'package:medpay/data/models/user_model.dart';
import 'package:medpay/data/payload/login_payload.dart';
import 'package:medpay/data/payload/register_payload.dart';
import 'package:medpay/utils/app_shared_preference.dart';

class AuthRepository {
  final ApiClient apiClient;

  AuthRepository({required this.apiClient});

  Future<Response> doRegister(RegisterPayload registerPayload) async {
    return await apiClient.post(RouteConstants.uriRegister, registerPayload.toJson(), headers: {});
  }

  Future<Response> doLogin(LoginPayload loginPayload) async {
    return await apiClient.post(RouteConstants.uriLogin, loginPayload.toJson(), headers: {});
  }

  Future<Response> getUserFromApi(String uuid) async {
    return await apiClient.get(RouteConstants.uriGETUser + uuid, headers: apiClient.mainHeaders);
  }

  void saveUserToLocalStorage(UserModel userModel, String password) {
    AppSharedPreference appSharedPreference = Get.find<AppSharedPreference>();
    String authToken = userModel.token!;
    appSharedPreference.storeValue(AppConstants.keyAuthToken, authToken);
    appSharedPreference.storeValue(AppConstants.keyUsername, userModel.username!);
    appSharedPreference.storeValue(AppConstants.keyPassword, password);
    appSharedPreference.storeValue(AppConstants.keyId, '${userModel.id!}');
    appSharedPreference.storeValue(AppConstants.keyUUID, userModel.uuid!);
    appSharedPreference.storeValue(AppConstants.keyName, userModel.name!);
    appSharedPreference.storeValue(AppConstants.keyPhone, userModel.phone!);
    appSharedPreference.storeValue(AppConstants.keyType, '${userModel.type}'.split(".")[1]);
    appSharedPreference.storeValue(AppConstants.keyNickName, '${userModel.nickName}');
    apiClient.token = authToken;
    apiClient.mainHeaders['Authorization'] = "Bearer $authToken";
  }

  Future<void> removeUserFromLocalStorage() async {
    AppSharedPreference sharedPreference = Get.find<AppSharedPreference>();
    await sharedPreference.removeAllUserSpecific();
  }

  UserModel? getUserFromLocalStorage() {
    AppSharedPreference sharedPreference = Get.find<AppSharedPreference>();
    var keyAuthToken = sharedPreference.getValue(AppConstants.keyAuthToken);
    var keyUsername = sharedPreference.getValue(AppConstants.keyUsername);
    var keyUID = sharedPreference.getValue(AppConstants.keyId);
    var keyName = sharedPreference.getValue(AppConstants.keyName);
    var keyPhone = sharedPreference.getValue(AppConstants.keyPhone);
    var id = sharedPreference.getValue(AppConstants.keyId);
    var accType = sharedPreference.getValue(AppConstants.keyType);
    AccountTypeEnum? userAccountType;
    if (accType != null) {
      if (accType.contains(".")) {
        userAccountType = AccountTypeEnum.values.byName(accType.split('.')[1]);
      } else {
        userAccountType = AccountTypeEnum.values.byName(accType);
      }
    }
    var nickName = sharedPreference.getValue(AppConstants.keyNickName);
    if (keyAuthToken != null && keyUsername != null && keyUID != null && keyName != null && keyPhone != null) {
      return UserModel(
          id: num.tryParse(id!),
          uuid: keyUID,
          username: keyUsername,
          phone: keyPhone,
          name: keyName,
          token: keyAuthToken,
          type: userAccountType,
          nickName: nickName);
    }
    return null;
  }
}
