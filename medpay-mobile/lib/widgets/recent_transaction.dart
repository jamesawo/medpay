import 'package:flutter/material.dart';
import 'package:medpay/data/models/transaction_model.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/style_utils.dart';
import 'package:medpay/widgets/recent_transaction_detail.dart';
import 'package:medpay/widgets/text_small.dart';

class RecentTransaction extends StatelessWidget {
  final String date;
  final List<TransactionModel> transactions;

  const RecentTransaction({
    required this.date,
    required this.transactions,
    Key? key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: EdgeInsets.only(top: AppDimensions.height2),
      child: Column(
        children: [
          // transaction date
          Container(
            padding: EdgeInsets.only(left: AppDimensions.width20),
            width: double.maxFinite,
            height: AppDimensions.height25,
            color: ThemeColorStyle.appGray20,
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                TextSmall(
                  size: AppDimensions.fontSize12,
                  text: date,
                  weight: FontWeight.w600,
                ),
              ],
            ),
          ),
          // transaction details
          Container(
            padding: EdgeInsets.only(left: AppDimensions.width20, right: AppDimensions.width20),
            child: Column(
              children: transactions
                  .map(
                    (transaction) => RecentTransactionDetail(transaction: transaction),
                  )
                  .toList(),
            ),
          ),
        ],
      ),
    );
  }
}
