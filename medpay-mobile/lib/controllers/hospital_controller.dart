import 'dart:convert';
import 'dart:developer';

import 'package:get/get.dart';
import 'package:medpay/data/constants/exception_constants.dart';
import 'package:medpay/data/models/hospital_model.dart';
import 'package:medpay/data/models/hospital_service.dart';
import 'package:medpay/utils/app_routes.dart';
import 'package:medpay/utils/helper_utils.dart';

import '../repositories/hospital_repository.dart';


class HospitalController extends GetxController {
  final HospitalRepository hospitalRepository;
  HospitalController({required this.hospitalRepository});

  // hospital list
  List<HospitalModel> _hospitalsList = [];
  List<HospitalModel> get hospitals => _hospitalsList;

  // selected services items
  List<HospitalService> _services = [];
  List<HospitalService> get services => _services;

  late HospitalModel? _selectedHospital;
  HospitalModel get selectedHospital => _selectedHospital!;
  set selectedHospital(HospitalModel hospital) {
    _selectedHospital = hospital;
    update();
  }

  bool _isLoading = false;
  bool get isLoading => _isLoading;
  set isLoading(bool value) {
    _isLoading = value;
    update();
  }

  bool isSearching = false;

  /// Retrieve hospital list from backend api
  /// Then store the result in [_hospitalsList]
  Future<void> getHospitalsFromApi() async {
    try {
      isLoading = true;
      Response response = await hospitalRepository.getHospitals();
      if (response.statusCode == 200) {
        List<dynamic> payload = jsonDecode(jsonEncode(response.body));
        List<HospitalModel> list = List<HospitalModel>.from(payload.map((hospital) => HospitalModel.fromJson(hospital)));
        isLoading = false;
        _hospitalsList = [];
        _hospitalsList.addAll(list);
        update();
      }
      isLoading = false;
    } catch (e) {
      isLoading = false;
      log(e.toString());
    }
  }

  /// Save user selected [hospital] to local storage
  ///
  /// So it can be retrieved later
  Future<void> saveUserHospitalToLocalStorage(HospitalModel hospital) async {
    if (hospital.id != null) {
      selectedHospital = hospital;
      hospitalRepository.saveUserHospitalToLocalStorage(hospital);
      HelperUtils.popBeforeNavigateTo(AppRoutes.homeMainScreen);
    } else {
      HelperUtils.showErrorSnackBar(
          title: ExceptionConstants.errorFriendlyTitle, message: ExceptionConstants.errorSelectHospital);
    }
  }

  Future<List<HospitalService>> searchServiceFromApi(String term, String hospitalId) async {
    try {
      isSearching = true;
      Response response = await hospitalRepository.searchHospitalService(term, hospitalId);
      if (response.statusCode == 200) {
        List<dynamic> payload = jsonDecode(jsonEncode(response.body));
        List<HospitalService> list = List<HospitalService>.from(payload.map((service) => HospitalService.fromJson(service)));
        update();
        isSearching = false;
        return list;
      }
      isSearching = false;
      return [];
    } catch (e) {
      isSearching = false;
      log(e.toString());
      return [];
    }
  }

  void addServiceToList(HospitalService service) {
    var contains = _services.where((element) => element.id == service.id);
    if (contains.isEmpty) {
      _services.add(service);
      update();
    }
  }

  void removeServiceFromList(int index) {
    _services.removeAt(index);
    update();
  }

  void clearSelectedServices() {
    _services = [];
    update();
  }

  // todo:: refactor this block to return HospitalModel and not a nullable HospitalModel,
  HospitalModel? getSavedHospitalFromLocalStorage() {
    var hospital = hospitalRepository.getSelectedHospitalFromLocalStorage();
    if (hospital != null) {
      selectedHospital = hospital;
    }
    return hospital;
  }

  Future<HospitalModel> getCurrentHospital() async {
    var hospitalModel = getSavedHospitalFromLocalStorage();
    if (hospitalModel != null && hospitalModel.id != null){
      var response = await  hospitalRepository.getCurrentHospitalDetail(hospitalModel.id.toString());
      if(response.isOk && response.body != null){
        var decode = jsonDecode(jsonEncode(response.body));
        return HospitalModel.fromJson(decode);
      }
    }

    // todo:: ask user to select hospital
    return HospitalModel();
  }
}
