import 'package:flutter/material.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/helper_utils.dart';
import 'package:medpay/utils/style_utils.dart';
import 'package:medpay/widgets/text_small.dart';

enum InputType { email, password, code, text, textArea, number }

class FormInputWithLabel extends StatefulWidget {
  final TextEditingController controller;
  final String label;
  final InputType? inputType;
  final IconData? prefixIcon;
  final IconData? suffixIcon;
  final String? inputHintText;
  final bool? isEnabled;
  final String? Function(String?)? validator;
  final bool? useAutoValidate;

  const FormInputWithLabel({
    required this.controller,
    required this.label,
    this.inputType = InputType.text,
    this.prefixIcon,
    this.suffixIcon,
    this.inputHintText,
    this.isEnabled = true,
    this.validator,
    this.useAutoValidate = false,
    Key? key,
  }) : super(key: key);

  @override
  State<FormInputWithLabel> createState() => _FormInputWithLabelState();
}

class _FormInputWithLabelState extends State<FormInputWithLabel> {
  bool _obscureText = true;

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      width: double.maxFinite,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          TextSmall(
            text: widget.label,
            size: AppDimensions.fontSize14,
          ),
          SizedBox(height: AppDimensions.height8),
          SizedBox(
            child: TextFormField(
              autovalidateMode: widget.useAutoValidate! ? AutovalidateMode.onUserInteraction : null,
              enabled: widget.isEnabled,
              controller: widget.controller,
              keyboardType: getKeyboardType(widget.inputType!),
              obscureText: widget.inputType == InputType.password ? _obscureText : false,
              textAlign: widget.inputType == InputType.code ? TextAlign.center : TextAlign.left,
              cursorHeight: widget.inputType == InputType.code ? null : 15,
              style: getStyle(widget.inputType!),
              decoration: getDecoration(widget.inputType!),
              validator:
                  widget.validator != null ? widget.validator! : (value) => HelperUtils.onValidateText(value, widget.label),
            ),
          )
        ],
      ),
    );
  }

  Widget getSuffixIcon(IconData? iconData) {
    if (widget.inputType == InputType.password) {
      return GestureDetector(
        onTap: () {
          setState(() {
            _obscureText = !_obscureText;
          });
        },
        child: _obscureText ? const Icon(Icons.visibility_off) : const Icon(Icons.visibility_outlined),
      );
    }
    return Icon(iconData);
  }

  TextInputType getKeyboardType(InputType inputType) {
    TextInputType type = TextInputType.text;
    switch (inputType) {
      case InputType.email:
        type = TextInputType.emailAddress;
        break;
      case InputType.password:
        type = TextInputType.visiblePassword;
        break;
      case InputType.code:
        type = TextInputType.number;
        break;
      case InputType.text:
        type = TextInputType.text;
        break;
      case InputType.textArea:
        type = TextInputType.multiline;
        break;
      case InputType.number:
        type = TextInputType.phone;
        break;
    }
    return type;
  }

  InputDecoration getDecoration(InputType inputType) {
    return inputType == InputType.code
        ? ThemeInputStyle.primaryCodeVerifyInputDecoration
        : InputDecoration(
            contentPadding: EdgeInsets.only(left: AppDimensions.width15),
            focusColor: ThemeColorStyle.appBlue,
            prefixIcon: widget.prefixIcon != null ? Icon(widget.prefixIcon) : null,
            border: OutlineInputBorder(
              borderRadius: BorderRadius.all(
                Radius.circular(AppDimensions.radius7),
              ),
            ),
            hintText: widget.inputHintText,
            hintStyle: TextStyle(
              fontSize: AppDimensions.fontSize12,
              fontStyle: FontStyle.normal,
              fontFamily: ThemeFontFamily.primaryFontFamily,
              fontWeight: FontWeight.w500,
              color: ThemeColorStyle.appGray10,
            ),
            suffixIcon: widget.suffixIcon != null ? getSuffixIcon(widget.suffixIcon) : null,
          );
  }

  TextStyle getStyle(InputType inputType) {
    return inputType == InputType.code
        ? TextStyle(
            fontFamily: ThemeFontFamily.primaryFontFamily,
            fontSize: AppDimensions.fontSize32,
            letterSpacing: 10,
            fontWeight: FontWeight.w600,
          )
        : TextStyle(
            fontFamily: ThemeFontFamily.primaryFontFamily,
            fontSize: AppDimensions.fontSize13,
            fontWeight: FontWeight.w400,
            fontStyle: FontStyle.normal,
          );
  }
}

/*
// text area
TextFormField(
   minLines: 6, // any number you need (It works as the rows for the textarea)
   keyboardType: TextInputType.multiline,
   maxLines: null,
)
 */
