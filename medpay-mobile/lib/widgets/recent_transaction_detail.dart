import 'package:flutter/material.dart';
import 'package:medpay/data/models/transaction_model.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/app_routes.dart';
import 'package:medpay/utils/helper_utils.dart';
import 'package:medpay/utils/style_utils.dart';
import 'package:medpay/widgets/text_medium.dart';
import 'package:medpay/widgets/text_small.dart';

class RecentTransactionDetail extends StatelessWidget {
  final TransactionModel transaction;

  const RecentTransactionDetail({
    required this.transaction,
    Key? key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        InkWell(
          onTap: () => navigateToPaymentStatusScreen(),
          child: Container(
            margin: EdgeInsets.only(top: AppDimensions.height5, bottom: AppDimensions.height5),
            child: Column(
              children: [
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    SizedBox(
                      child: Row(
                        children: [
                          SizedBox(
                            width: AppDimensions.width10,
                            child: CircleAvatar(
                              backgroundColor: isSuccessfulTransaction() ? ThemeColorStyle.appLightGreen : ThemeColorStyle.appRed,
                            ),
                          ),
                          SizedBox(width: AppDimensions.width15),
                          Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              TextMedium(
                                text: 'Serial #: ${transaction.token}',
                                size: AppDimensions.fontSize14,
                                weight: FontWeight.w400,
                              ),
                              SizedBox(height: AppDimensions.height2),
                              TextSmall(
                                size: AppDimensions.fontSize12,
                                text: 'Reference #: ${transaction.reference}',
                                color: ThemeColorStyle.appGray50,
                                weight: FontWeight.w500,
                              ),
                            ],
                          ),
                        ],
                      ),
                    ),
                    Column(
                      crossAxisAlignment: CrossAxisAlignment.end,
                      children: [
                        TextMedium(
                          text: '${transaction.amount}',
                          size: AppDimensions.fontSize13,
                          weight: FontWeight.w600,
                        ),
                        SizedBox(height: AppDimensions.height2),
                        TextSmall(
                          size: AppDimensions.fontSize12,
                          text: isSuccessfulTransaction() ? 'Successful' : 'Failed',
                          color: isSuccessfulTransaction() ? ThemeColorStyle.appLightGreen : ThemeColorStyle.appRed,
                          weight: FontWeight.w600,
                        ),
                      ],
                    ),
                  ],
                ),
                SizedBox(height: AppDimensions.height5),
              ],
            ),
          ),
        ),
        Divider(height: AppDimensions.height2, color: ThemeColorStyle.appGray10),
      ],
    );
  }

  bool isSuccessfulTransaction() => transaction.isSuccessfulTransaction();

  void navigateToPaymentStatusScreen() {
    HelperUtils.popBeforeNavigateToWithArgs(
      AppRoutes.paymentStatusScreen,
      {'transaction': transaction, 'buttonText': 'Go Back'},
    );
  }
}
