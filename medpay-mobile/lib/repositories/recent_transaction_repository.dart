import 'package:get/get.dart';
import 'package:medpay/data/api/api_client.dart';
import 'package:medpay/data/constants/route_constants.dart';
import 'package:medpay/utils/app_shared_preference.dart';

class RecentTransactionRepository extends GetxService {
  final ApiClient apiClient;

  RecentTransactionRepository({required this.apiClient});

  Future<Response> onGetRecentTransactions() async {
    var sharedPref = Get.find<AppSharedPreference>();
    String hospital = sharedPref.getHospitalUuid();
    String user = sharedPref.getUserUuid();
    return await apiClient.get(
      RouteConstants.uriGetTransactions + '?user=$user&hospital=$hospital',
      headers: apiClient.mainHeaders,
    );
  }
}
