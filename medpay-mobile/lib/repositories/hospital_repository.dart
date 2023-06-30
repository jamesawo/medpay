import 'package:get/get.dart';
import 'package:medpay/data/api/api_client.dart';
import 'package:medpay/data/constants/app_constants.dart';
import 'package:medpay/data/constants/route_constants.dart';
import 'package:medpay/data/enums/app_enum.dart';
import 'package:medpay/data/models/hospital_model.dart';
import 'package:medpay/utils/app_shared_preference.dart';

class HospitalRepository {
  final ApiClient apiClient;

  HospitalRepository({required this.apiClient});

  Future<Response> getHospitals() async {
    return await apiClient.get(RouteConstants.uriGETHospitals, headers: apiClient.mainHeaders);
  }

  Future<Response> searchHospitalService(String term, String hospitalId) async {
    String query = '?title=$term&hospital=$hospitalId';
    return await apiClient.get(RouteConstants.uriSearchHospitalService + query, headers: apiClient.mainHeaders);
  }

  HospitalModel? getSelectedHospitalFromLocalStorage() {
    var preference = Get.find<AppSharedPreference>();
    var hospitalUuid = preference.getValue(AppConstants.keyHospitalId);
    var hospitalName = preference.getValue(AppConstants.keyHospitalName);
    var hospitalLogoUrl = preference.getValue(AppConstants.keyHospitalLogoUrl);
    var hospitalAddress = preference.getValue(AppConstants.keyHospitalAddress);
    var useHospitalSoftware = preference.getValue(AppConstants.keyHospitalHasSoftware);
    var modeName = preference.getValue(AppConstants.keyHospitalCollectionMode);

    if (hospitalName != null && hospitalUuid != null) {
      return HospitalModel(
        id: hospitalUuid,
        title: hospitalName,
        address: hospitalAddress,
        logoUrl: hospitalLogoUrl,
        useHospitalSoftware: useHospitalSoftware == 'true',
        collectionModelEnum: CollectionModeEnum.values.byName(modeName!),
      );
    }
    return null;
  }

  void saveUserHospitalToLocalStorage(HospitalModel hospital) async {
    var preference = Get.find<AppSharedPreference>();
    preference.storeValue(AppConstants.keyHospitalId, hospital.id!);
    preference.storeValue(AppConstants.keyHospitalName, hospital.title!);
    preference.storeValue(AppConstants.keyHospitalLogoUrl, '${hospital.logoUrl}');
    preference.storeValue(AppConstants.keyHospitalAddress, '${hospital.address}');
    preference.storeValue(AppConstants.keyHospitalHasSoftware, '${hospital.useHospitalSoftware}');
    preference.storeValue(AppConstants.keyHospitalCollectionMode, hospital.collectionModelEnum!.name);
  }
}
