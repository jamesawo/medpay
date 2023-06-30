import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:medpay/controllers/hospital_controller.dart';
import 'package:medpay/data/models/hospital_service.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/style_utils.dart';

class ServiceItemsWidget extends StatelessWidget {
  final List<HospitalService>? items;

  const ServiceItemsWidget({this.items, Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return GetBuilder<HospitalController>(builder: (controller) {
      return Expanded(
        child: Container(
          margin: EdgeInsets.symmetric(horizontal: AppDimensions.width25),
          padding: const EdgeInsets.all(0),
          child: getListView(controller),
        ),
      );
    });
  }

  ListView getListView(HospitalController controller) {
    return ListView.builder(
        itemCount: controller.services.length,
        itemBuilder: (context, index) => ListTile(
              leading: InkWell(
                onTap: () {
                  controller.removeServiceFromList(index);
                },
                child: const Icon(
                  Icons.delete_forever,
                  color: ThemeColorStyle.appRed,
                ),
              ),
              title: Text(controller.services[index].title),
              subtitle: Text("code: ${controller.services[index].code}"),
            ));
  }
}
