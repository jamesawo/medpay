import 'dart:convert';
import 'dart:developer';

import 'package:get/get.dart';
import 'package:medpay/controllers/hospital_controller.dart';
import 'package:medpay/controllers/recent_transaction_controller.dart';
import 'package:medpay/data/constants/app_constants.dart';
import 'package:medpay/data/constants/exception_constants.dart';
import 'package:medpay/data/enums/app_enum.dart';
import 'package:medpay/data/models/hospital_model.dart';
import 'package:medpay/data/models/user_model.dart';
import 'package:medpay/data/payload/login_payload.dart';
import 'package:medpay/data/payload/register_payload.dart';
import 'package:medpay/data/payload/response.payload.dart';
import 'package:medpay/repositories/auth_repository.dart';
import 'package:medpay/utils/app_routes.dart';
import 'package:medpay/utils/app_shared_preference.dart';
import 'package:medpay/utils/helper_utils.dart';

class AuthController extends GetxController implements GetxService {
  final AuthRepository authRepository;
  AuthController({required this.authRepository});

  AuthStatusEnum _authStatusEnum = AuthStatusEnum.loggedOut;
  get authStatus => _authStatusEnum;
  set _authStatus(AuthStatusEnum statusEnum) {
    _authStatusEnum = statusEnum;
    update();
  }

  /* todo create a local state and move current user to local state class
      move to local state
   */
  UserModel _currentLoggedInUser = UserModel();
  UserModel get loggedInUser => _currentLoggedInUser;
  set loggedInUser(UserModel user) {
    _currentLoggedInUser = user;
    update();
  }

  Future<Response> doRegister(RegisterPayload registerPayload) async {
    try {
      _authStatus = AuthStatusEnum.authenticating;
      Response response = await authRepository.doRegister(registerPayload);

      if (response.statusCode == 201) {
        // transform payload
        ResponsePayload payload = ResponsePayload.fromJson(jsonDecode(jsonEncode(response.body)));
        // transform response to user model
        UserModel userModel = UserModel.fromJson(payload.data);
        // save user token for next request
        authRepository.saveUserToLocalStorage(userModel, registerPayload.password);
        loggedInUser = userModel;
        // show success notification && update ui
        HelperUtils.showSuccessSnackBar(
          message: MessageConstants.verifyEmail,
          title: payload.message!,
          duration: 8,
        );
        _authStatus = AuthStatusEnum.registered;
        // pop screen
        HelperUtils.popBeforeNavigateToWithArgs(AppRoutes.homeSetLocationScreen, true);
        return response;
      }
      onShowError(response, AuthStatusEnum.notRegistered);
      return response;
    } on Exception catch (e) {
      log(e.toString());
      _authStatus = AuthStatusEnum.notRegistered;
      return Response(statusCode: 1, statusText: e.toString());
    }
  }

  Future<Response> doLogin(LoginPayload loginPayload) async {
    try {
      _authStatus = AuthStatusEnum.authenticating;
      Response response = await authRepository.doLogin(loginPayload);
      if (response.statusCode == 200) {
        UserModel user = UserModel.fromJson(jsonDecode(jsonEncode(response.body)));
        authRepository.saveUserToLocalStorage(user, loginPayload.password);
        loggedInUser = user;
        _authStatus = AuthStatusEnum.loggedIn;
        setHospitalIfPresent(response.body['hospital']);
        // navigate to home screen
        HelperUtils.popBeforeNavigateTo(AppRoutes.homeMainScreen);
        return response;
      }
      onShowError(response, AuthStatusEnum.notLoggedIn);
      return response;
    } catch (e) {
      log(e.toString());
      _authStatus = AuthStatusEnum.notLoggedIn;
      return Response(statusCode: 1, statusText: e.toString());
    }
  }

  void setHospitalIfPresent(hospitalJson) {
    if (hospitalJson != null) {
      HospitalController hospitalController = Get.find<HospitalController>();
      HospitalModel model = HospitalModel.fromJson(hospitalJson);
      hospitalController.saveUserHospitalToLocalStorage(model);
    }
  }

  Future<UserModel?> getUserFromApi() async {
    String? userUuid = Get.find<AppSharedPreference>().getValue(AppConstants.keyId);
    Response response = await authRepository.getUserFromApi(userUuid!);
    if (response.statusCode == 200) {
      UserModel userModel = UserModel.fromJson(jsonDecode(jsonEncode(response.body['data'])));
      loggedInUser = userModel;
      update();
      return userModel;
    }
    return null;
  }

  void onShowError(Response response, AuthStatusEnum statusEnum) {
    _authStatus = statusEnum;
    if (response.body != null) {
      ResponsePayload payload = ResponsePayload.fromJson(jsonDecode(jsonEncode(response.body)));
      HelperUtils.showErrorSnackBar(title: payload.message, message: payload.error);
    } else {
      HelperUtils.showErrorSnackBar(title: ExceptionConstants.errorDefaultTitle, message: ExceptionConstants.errorDefaultMessage);
    }
  }

  void doLogout() async {
    loggedInUser = UserModel();
    await authRepository.removeUserFromLocalStorage();
    Get.find<RecentTransactionController>().clearTransactions();
    Get.find<HospitalController>().clearSelectedServices();
    HelperUtils.navigateToAndRemoveAll(AppRoutes.authLoginScreen);
  }

  void removeUserFromLocalStorage() async {
    await authRepository.removeUserFromLocalStorage();
  }

  bool isUserLoggedIn() {
    if (loggedInUser.isEmpty()) {
      UserModel? user = authRepository.getUserFromLocalStorage();
      loggedInUser = user ?? UserModel();
      return user != null;
    }
    return true;
  }

  void reAuthenticateUser() async {
    doLogout();
    /*AppSharedPreference sharedPreference = Get.find<AppSharedPreference>();
    String? username = sharedPreference.getValue(AppConstants.keyUsername);
    String? password = sharedPreference.getValue(AppConstants.keyPassword);
    if (username != null && password != null) {
      LoginPayload loginPayload = LoginPayload(username, password);
      Response response = await authRepository.doLogin(loginPayload);
      if (response.statusCode == 200) {
        UserModel user = UserModel.fromJson(jsonDecode(jsonEncode(response.body['data'])));
        authRepository.saveUserToLocalStorage(user, loginPayload.password);
        loggedInUser = user;
      } else {
        doLogout();
      }
    } else {
      doLogout();
    }*/
  }
}
