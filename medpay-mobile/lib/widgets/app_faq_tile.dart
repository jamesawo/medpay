import 'package:flutter/material.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/style_utils.dart';
import 'package:medpay/widgets/text_medium.dart';
import 'package:medpay/widgets/text_small.dart';

import 'app_icon.dart';

class AppFaqWidgetTile extends StatefulWidget {
  final String question;
  final String answer;
  final bool? isOpen;

  const AppFaqWidgetTile({
    required this.question,
    required this.answer,
    this.isOpen = false,
    Key? key,
  }) : super(key: key);

  @override
  State<AppFaqWidgetTile> createState() => _AppFaqWidgetTileState();
}

class _AppFaqWidgetTileState extends State<AppFaqWidgetTile> {
  @override
  Widget build(BuildContext context) {
    return ExpansionTile(
      key: const PageStorageKey('faq'),
      title: SizedBox(
        width: double.infinity,
        child: TextMedium(
          color: ThemeColorStyle.appBlack,
          text: widget.question,
          overflow: TextOverflow.visible,
          size: AppDimensions.fontSize15,
        ),
      ),
      trailing: AppIcon(
        iconSize: AppDimensions.fontSize18,
        iconData: Icons.arrow_drop_down,
        shapeWidth: AppDimensions.width30,
        shapeHeight: AppDimensions.height25,
      ),
      onExpansionChanged: (value) {
        setState(() {
          // var isExpand = value;
        });
      },
      children: [
        Container(
          margin: EdgeInsets.only(top: AppDimensions.height10, left: AppDimensions.width15, bottom: AppDimensions.height10),
          child: TextSmall(
            overflow: TextOverflow.visible,
            size: AppDimensions.fontSize14,
            text: widget.answer,
          ),
        ),
      ],
    );
  }
}
