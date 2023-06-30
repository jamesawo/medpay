import 'package:get/get.dart';
import 'package:medpay/data/api/api_client.dart';

class UserRepository extends GetxService {
  final ApiClient apiClient;
  UserRepository({required this.apiClient});

  Future<Response> getUserDetail() async {
    // return await apiClient.getData('uri');
    return Future.value(Response());
  }
}
