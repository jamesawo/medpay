import 'package:get/get.dart';
import 'package:medpay/controllers/auth_controller.dart';
import 'package:medpay/data/constants/app_constants.dart';
import 'package:medpay/utils/app_shared_preference.dart';

class ApiClient extends GetConnect implements GetxService {
  late String token;
  late Map<String, String> mainHeaders;

  ApiClient() {
    baseUrl = "";
    timeout = const Duration(seconds: 40);
    sendUserAgent = true;
    token = Get.find<AppSharedPreference>().getValue(AppConstants.keyAuthToken) ?? "";
    mainHeaders = {'Authorization': 'Bearer $token'};

    httpClient.addResponseModifier((request, response) async {
      if (response.statusCode! > 400) printError(info: 'request failed with status code:  ${response.statusCode}');
      return response;
    });

    /*
    * httpClient.addAuthenticator "only" is called after a request (get/post/put/delete)
    * that returns HTTP status code 401.
    * It is useful when your token expires and you need to obtain a new token.
     */
    httpClient.addAuthenticator<dynamic>((request) async {
      print(request);
      print('re authenticating');
      Get.find<AuthController>().reAuthenticateUser();
      return request;
    });

    /*
    * It is necessary to use httpClient.maxAuthRetries, when using addAuthenticator
     */
    httpClient.maxAuthRetries = 2;
  }
}
