import 'package:flutter/material.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/style_utils.dart';

class SearchInput extends StatefulWidget {
  final TextEditingController controller;
  final String? inputHintText;

  const SearchInput({
    required this.controller,
    this.inputHintText = 'Enter your search term',
    Key? key,
  }) : super(key: key);

  @override
  State<SearchInput> createState() => _SearchInputState();
}

class _SearchInputState extends State<SearchInput> {
  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      height: AppDimensions.height45,
      width: double.maxFinite,
      child: TextFormField(
        controller: widget.controller,
        keyboardType: TextInputType.text,
        textAlign: TextAlign.left,
        cursorHeight: AppDimensions.height15,
        style: ThemeInputStyle.searchInputStyle,
        decoration: ThemeInputStyle.getSearchInputDecoration(widget.inputHintText!),
      ),
    );
  }
}
