import 'package:flutter/material.dart';
import 'package:flutter_typeahead/flutter_typeahead.dart';
import 'package:get/get.dart';
import 'package:medpay/controllers/hospital_controller.dart';
import 'package:medpay/data/models/hospital_model.dart';
import 'package:medpay/data/models/hospital_service.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/app_routes.dart';
import 'package:medpay/utils/helper_utils.dart';
import 'package:medpay/utils/style_utils.dart';
import 'package:medpay/widgets/app_icon_button.dart';
import 'package:medpay/widgets/text_small.dart';

class SearchAutoCompleteInputWithButton extends StatefulWidget {
  final String? hintText;
  final bool isItemsScreen;
  final TextEditingController? textController;

  const SearchAutoCompleteInputWithButton({
    this.hintText,
    required this.isItemsScreen,
    this.textController,
    Key? key,
  }) : super(key: key);

  @override
  State<SearchAutoCompleteInputWithButton> createState() => _SearchAutoCompleteInputWithButtonState();
}

class _SearchAutoCompleteInputWithButtonState extends State<SearchAutoCompleteInputWithButton> {
  late TextEditingController _searchInputController;
  late HospitalController _hospitalController;
  late HospitalModel? _currentHospital;
  HospitalService? _selectedService;

  @override
  void initState() {
    super.initState();
    _searchInputController = (widget.textController ?? TextEditingController());
    _searchInputController.text = widget.textController?.text ?? '';
    _currentHospital = Get.find<HospitalController>().selectedHospital;
    _hospitalController = Get.find<HospitalController>();
  }

  @override
  Widget build(BuildContext context) {
    return Row(
      children: [
        Expanded(
          child: getSearchInput(),
        ),
        SizedBox(width: AppDimensions.height5),
        AppIconButton(
          child: widget.isItemsScreen == true ? const Icon(Icons.add) : const Icon(Icons.search),
          action: () {
            if (_selectedService == null) {
              _searchInputController.text = '';
              // return;
            }
            onActionButtonClicked();
          },
        ),
      ],
    );
  }

  Widget loadingSuggestions(BuildContext context) {
    return Container(
      decoration: const BoxDecoration(
        borderRadius: BorderRadius.zero,
        color: ThemeColorStyle.appWhite20,
      ),
      padding: EdgeInsets.all(AppDimensions.height10),
      height: AppDimensions.height30,
      width: AppDimensions.width273,
      child: const Center(
        child: LinearProgressIndicator(),
      ),
    );
  }

  Widget getSearchInput() {
    return SizedBox(
      height: AppDimensions.height45,
      width: double.maxFinite,
      child: TypeAheadField(
        hideOnLoading: false,
        loadingBuilder: loadingSuggestions,
        minCharsForSuggestions: 3,
        textFieldConfiguration: TextFieldConfiguration(
          controller: _searchInputController,
          autofocus: false,
          style: ThemeInputStyle.searchInputStyle,
          decoration: ThemeInputStyle.getSearchInputDecoration(
            widget.hintText ?? 'Start typing to search..',
            showClearIcon: true,
            action: () {
              _searchInputController.text = '';
            },
          ),
        ),
        suggestionsCallback: (pattern) async {
          return _hospitalController.searchServiceFromApi(pattern, '${_currentHospital?.id}');
        },
        itemBuilder: (context, HospitalService suggestion) {
          return ListTile(
            hoverColor: ThemeColorStyle.appOrange,
            title: TextSmall(
              text: suggestion.title,
              size: AppDimensions.fontSize15,
            ),
            style: ListTileStyle.list,
          );
        },
        onSuggestionSelected: (HospitalService suggestion) => onSelected(suggestion),
      ),
    );
  }

  void onSelected(HospitalService selected) {
    _searchInputController.text = selected.title;
    _selectedService = selected;
  }

  void onActionButtonClicked() {
    if (!widget.isItemsScreen) {
      // goto add items screen
      HelperUtils.navigateToWithArgs(
        AppRoutes.paymentAddServiceItemsScreen,
        _selectedService,
      );
    } else {
      if (_selectedService != null) {
        _hospitalController.addServiceToList(_selectedService!);
      }
    }
  }
}
