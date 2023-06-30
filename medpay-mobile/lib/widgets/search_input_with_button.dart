import 'package:flutter/material.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/app_routes.dart';
import 'package:medpay/utils/helper_utils.dart';
import 'package:medpay/widgets/app_icon_button.dart';
import 'package:medpay/widgets/search_input.dart';

class SearchInputWithButton extends StatefulWidget {
  final String? searchText;
  final String? hintText;
  final VoidCallback? action;
  final TextEditingController? textController;

  const SearchInputWithButton({
    this.searchText,
    this.hintText = 'Enter search term',
    this.action,
    this.textController,
    Key? key,
  }) : super(key: key);

  @override
  State<SearchInputWithButton> createState() => _SearchInputWithButtonState();
}

class _SearchInputWithButtonState extends State<SearchInputWithButton> {
  late TextEditingController _searchInputController;

  @override
  void initState() {
    super.initState();
    _searchInputController = (widget.textController ?? TextEditingController());
    _searchInputController.text = widget.searchText ?? 'INV200987';
  }

  @override
  Widget build(BuildContext context) {
    return Row(
      children: [
        Expanded(
          child: SearchInput(
            controller: _searchInputController,
            inputHintText: widget.hintText,
          ),
        ),
        SizedBox(width: AppDimensions.height5),
        AppIconButton(
          action: widget.action ?? () => HelperUtils.navigateTo(AppRoutes.paymentBillSearchScreen),
        ),
      ],
    );
  }
}
