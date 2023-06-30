import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:medpay/data/enums/app_enum.dart';
import 'package:medpay/data/models/action_type.dart';
import 'package:medpay/data/models/transaction_model.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/helper_utils.dart';
import 'package:medpay/utils/style_utils.dart';
import 'package:medpay/widgets/app_top_title.dart';
import 'package:medpay/widgets/text_small.dart';
import 'package:medpay/widgets/transaction_not_successful.dart';
import 'package:medpay/widgets/transaction_successful.dart';

class PaymentStatusScreen extends StatelessWidget {
  final TransactionModel transaction;
  final String? buttonText;

  const PaymentStatusScreen({Key? key, required this.transaction, this.buttonText}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return AnnotatedRegion<SystemUiOverlayStyle>(
      value: SystemUiOverlayStyle.dark,
      child: Scaffold(
        resizeToAvoidBottomInset: true,
        backgroundColor: ThemeColorStyle.appWhite,
        body: SafeArea(
          child: SingleChildScrollView(
            scrollDirection: Axis.vertical,
            reverse: false,
            child: Column(
              children: [
                AppTopTitle(
                  title: 'Payment Status',
                  showMoreVert: true,
                  action: navigateToDashboardScreen,
                  actionList: getVertActions(),
                ),
                SizedBox(
                  height: AppDimensions.height500,
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    crossAxisAlignment: CrossAxisAlignment.center,
                    mainAxisSize: MainAxisSize.max,
                    children: [
                      transaction.statusEnum == TransactionStatusEnum.SUCCESSFUL
                          ? TransactionSuccessful(
                              transaction: transaction,
                            )
                          : TransactionNotSuccessful(transaction: transaction),
                      SizedBox(height: AppDimensions.height30),
                      getActionButton()
                    ],
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }

  Widget getActionButton() {
    return ElevatedButton(
      style: ThemeInputStyle.primaryButtonStyle,
      child: TextSmall(
        text: buttonText ?? 'Make New Payment',
        size: AppDimensions.fontSize16,
        color: ThemeColorStyle.appWhite,
        weight: FontWeight.w600,
      ),
      onPressed: () => navigateToDashboardScreen(),
    );
  }

  void navigateToDashboardScreen() {
    HelperUtils.navigateToMainAndRemoveAll();
  }

  List<ActionModel> getVertActions() {
    if (transaction.statusEnum == TransactionStatusEnum.SUCCESSFUL) {
      return [
        ActionModel(displayTitle: "Download Receipt", action: onDownloadReceipt),
        ActionModel(displayTitle: "Print Receipt", action: onPrintReceipt),
        ActionModel(displayTitle: "Preview Receipt", action: onPreviewReceipt),
      ];
    }
    return [
      ActionModel(displayTitle: "Contact Support", action: onGetHelpClicked),
    ];
  }

  void onDownloadReceipt() {
    // check transaction status first
    // only download successful transaction
  }

  void onPrintReceipt() {}

  void onPreviewReceipt() {}

  void onGetHelpClicked() {}
}
