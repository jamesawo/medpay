import 'package:get/get.dart';
import 'package:get/get_connect/http/src/request/request.dart';
import 'package:medpay/data/constants/app_constants.dart';
import 'package:medpay/utils/app_shared_preference.dart';

class BaseProvider extends GetConnect {
  BaseProvider();
  late String? token;
  late Map<String, String> headers;

  @override
  void onInit() {
    token = Get.find<AppSharedPreference>().getValue(AppConstants.keyAuthToken) ?? '';
    headers = {'Authorization': "Bearer $token"};

    httpClient.baseUrl = '';
    httpClient.timeout = const Duration(seconds: AppConstants.appTimeoutSeconds);
    httpClient.sendUserAgent = true;

    httpClient.addResponseModifier((request, response) async {
      return response; // add this
    });

    httpClient.addRequestModifier<dynamic>((Request request) async {
      if (token != null && token!.isNotEmpty) {
        request.headers.addAll(headers);
      }
      return request;
    });
    super.onInit();
  }
}
