import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:medpay/controllers/hospital_controller.dart';
import 'package:medpay/data/constants/exception_constants.dart';
import 'package:medpay/utils/app_routes.dart';
import 'package:medpay/utils/helper_utils.dart';
import 'package:medpay/widgets/search_input_with_button.dart';

import 'search_autocomplete_input_with_button.dart';

class BillOrServiceSearchInput extends StatefulWidget {
  const BillOrServiceSearchInput({Key? key}) : super(key: key);

  @override
  State<BillOrServiceSearchInput> createState() => _BillOrServiceSearchInputState();
}

class _BillOrServiceSearchInputState extends State<BillOrServiceSearchInput> {
  late TextEditingController _textController;

  @override
  void initState() {
    super.initState();
    _textController = TextEditingController();
    _textController.text = '';
  }

  @override
  void dispose() {
    _textController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return GetBuilder<HospitalController>(
      builder: (HospitalController controller) {
        return Container(
          child: controller.selectedHospital.useHospitalSoftware != true ? getServiceAutoSearchInput() : getBillSearch(),
        );
      },
    );
  }

  SearchAutoCompleteInputWithButton getServiceAutoSearchInput() {
    return SearchAutoCompleteInputWithButton(
      isItemsScreen: false,
      textController: _textController,
      hintText: 'Search for service ...',
    );
  }

  SearchInputWithButton getBillSearch() {
    return SearchInputWithButton(
        textController: _textController,
        searchText: _textController.text,
        hintText: 'Enter your bill number',
        action: () {
          String searchTerm = _textController.text;
          if (searchTerm.isNotEmpty) {
            HelperUtils.navigateToWithArgs(
              AppRoutes.paymentBillSearchScreen,
              searchTerm,
            );
          } else {
            HelperUtils.showErrorSnackBar(
              title: ExceptionConstants.errorFriendlyTitle,
              message: ExceptionConstants.errorEmptyBillSearch,
            );
          }
        });
  }
}
