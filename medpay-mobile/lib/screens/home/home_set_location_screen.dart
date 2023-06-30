import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:get/get.dart';
import 'package:medpay/controllers/hospital_controller.dart';
import 'package:medpay/data/models/hospital_model.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/style_utils.dart';
import 'package:medpay/widgets/app_center_loading.dart';
import 'package:medpay/widgets/search_input_with_button.dart';
import 'package:medpay/widgets/text_large.dart';
import 'package:medpay/widgets/text_medium.dart';
import 'package:medpay/widgets/text_small.dart';

class HomeSetLocationScreen extends StatefulWidget {
  final bool? showTitle;

  const HomeSetLocationScreen({this.showTitle = true, Key? key}) : super(key: key);

  @override
  State<HomeSetLocationScreen> createState() => _HomeSetLocationScreenState();
}

class _HomeSetLocationScreenState extends State<HomeSetLocationScreen> {
  HospitalModel? selectedHospital = HospitalModel();
  int selectedIndex = -1;

  @override
  void initState() {
    super.initState();
    HospitalController controller = Get.find<HospitalController>();
    if (controller.hospitals.isEmpty) {
      controller.getHospitalsFromApi();
    }
    if (controller.getSavedHospitalFromLocalStorage() != null) {
      var selected = controller.selectedHospital;
      var index = controller.hospitals.indexWhere((element) => element.id == selected.id);
      onSetSelectedHospital(controller.selectedHospital, index);
    }
  }

  void onSetSelectedHospital(HospitalModel hospital, int index) {
    setState(() {
      selectedIndex = index;
      selectedHospital = hospital;
    });
  }

  Future<void> onRefreshData() async {
    Get.find<HospitalController>().getHospitalsFromApi();
  }

  @override
  Widget build(BuildContext context) {
    return GetBuilder<HospitalController>(builder: (controller) {
      return RefreshIndicator(
        onRefresh: () => onRefreshData(),
        child: AnnotatedRegion<SystemUiOverlayStyle>(
          value: SystemUiOverlayStyle.dark,
          child: Scaffold(
            body: SafeArea(
              child: NestedScrollView(
                floatHeaderSlivers: true,
                headerSliverBuilder: (
                  BuildContext context,
                  bool innerBoxIsScrolled,
                ) {
                  return <Widget>[
                    SliverToBoxAdapter(
                      child: Container(
                        margin: EdgeInsets.only(left: AppDimensions.width20, right: AppDimensions.width20),
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            SizedBox(height: AppDimensions.height40),
                            TextLarge(
                              text: 'One more thing',
                              size: AppDimensions.fontSize32,
                            ),
                            SizedBox(height: AppDimensions.height5),
                            Row(
                              mainAxisAlignment: MainAxisAlignment.spaceBetween,
                              children: [
                                TextMedium(
                                  text: 'Select your Hospital Location',
                                  size: AppDimensions.fontSize18,
                                ),
                                GestureDetector(
                                  onTap: () {
                                    onRefreshData();
                                  },
                                  child: const Icon(Icons.refresh, color: ThemeColorStyle.appOrange),
                                ),
                              ],
                            ),
                            SizedBox(height: AppDimensions.height20),
                            const SearchInputWithButton(searchText: ''),
                          ],
                        ),
                      ),
                    ),
                  ];
                },
                body: controller.isLoading
                    ? const AppPageLoading()
                    : ListView.builder(
                        padding: EdgeInsets.all(AppDimensions.height5),
                        itemCount: controller.hospitals.length,
                        itemBuilder: (BuildContext context, int index) {
                          return Column(
                            mainAxisSize: MainAxisSize.min,
                            children: [
                              ListTile(
                                title: TextSmall(text: controller.hospitals[index].title!, size: AppDimensions.fontSize15),
                                leading: Radio<int>(
                                  groupValue: selectedIndex,
                                  value: index,
                                  onChanged: (int? value) => onSetSelectedHospital(controller.hospitals[index], index),
                                ),
                                onTap: () => onSetSelectedHospital(controller.hospitals[index], index),
                              ),
                              Divider(
                                height: AppDimensions.height5,
                                color: ThemeColorStyle.appGray20,
                                thickness: AppDimensions.height2,
                              ),
                            ],
                          );
                        }),
              ),
            ),
            bottomSheet: Container(
              height: AppDimensions.height100,
              width: double.maxFinite,
              color: ThemeColorStyle.appWhite,
              padding: EdgeInsets.only(top: AppDimensions.height10),
              margin: EdgeInsets.symmetric(horizontal: AppDimensions.width25, vertical: AppDimensions.height10),
              child: Column(
                children: [
                  ElevatedButton(
                    style: ThemeInputStyle.primaryButtonStyle,
                    child: TextLarge(
                      text: 'Proceed',
                      color: ThemeColorStyle.appWhite,
                      size: AppDimensions.fontSize16,
                    ),
                    onPressed: () {
                      controller.saveUserHospitalToLocalStorage(selectedHospital!);
                    },
                  ),
                ],
              ),
            ),
          ),
        ),
      );
    });
  }
}
