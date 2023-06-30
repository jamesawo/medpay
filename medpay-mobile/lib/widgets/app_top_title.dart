import 'package:flutter/material.dart';
import 'package:medpay/data/models/action_type.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/helper_utils.dart';
import 'package:medpay/utils/style_utils.dart';
import 'package:medpay/widgets/text_large.dart';
import 'package:medpay/widgets/text_small.dart';

class AppTopTitle extends StatelessWidget {
  final IconData? prefixIcon;
  final String title;
  final VoidCallback? action;
  final bool? showPrefixIcon;
  final bool? showMoreVert;
  final List<ActionModel>? actionList;

  const AppTopTitle({
    this.prefixIcon = Icons.arrow_back_outlined,
    required this.title,
    this.action,
    this.showPrefixIcon = true,
    this.showMoreVert = false,
    this.actionList,
    Key? key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: EdgeInsets.only(
        left: AppDimensions.width25,
        right: AppDimensions.width20,
        top: AppDimensions.height10,
      ),
      width: double.maxFinite,
      height: AppDimensions.height70,
      child: Row(
        children: [
          showPrefixIcon!
              ? InkWell(
                  onTap: action ??
                      () {
                        HelperUtils.popOnly();
                      },
                  child: Container(
                    width: AppDimensions.width40,
                    height: AppDimensions.height50,
                    color: ThemeColorStyle.appWhite,
                    child: Icon(prefixIcon, color: ThemeColorStyle.appBlack),
                  ),
                )
              : Container(),
          Expanded(
            child: TextLarge(
              align: TextAlign.center,
              text: title,
              size: AppDimensions.fontSize22,
            ),
          ),
          showMoreVert!
              ? Container(
                  color: Colors.white,
                  width: AppDimensions.width40,
                  height: AppDimensions.height50,
                  child: PopupMenuButton(
                    child: const Icon(Icons.more_vert_outlined, color: ThemeColorStyle.appBlack),
                    itemBuilder: (BuildContext context) {
                      return getVertPopupMenu();
                    },
                  ),
                )
              : SizedBox(
                  width: AppDimensions.width40,
                  height: AppDimensions.height50,
                ),
        ],
      ),
    );
  }

  List<PopupMenuItem> getVertPopupMenu() {
    if (actionList != null && actionList!.isNotEmpty) {
      return List<PopupMenuItem>.from(
        actionList!.map(
          (actionModel) => PopupMenuItem(
            child: InkWell(
              onTap: actionModel.action,
              child: TextSmall(
                text: '${actionModel.displayTitle}',
                size: AppDimensions.fontSize13,
              ),
            ),
          ),
        ),
      ).toList();
    } else {
      return [];
    }
  }
}
