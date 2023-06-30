import 'package:flutter/material.dart';
import 'package:medpay/data/models/transaction_model.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/style_utils.dart';
import 'package:medpay/widgets/text_medium.dart';

class TransactionSuccessful extends StatelessWidget {
  final TransactionModel transaction;

  const TransactionSuccessful({required this.transaction, Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: EdgeInsets.only(left: AppDimensions.width30, right: AppDimensions.width30),
      child: Card(
        elevation: 4,
        shadowColor: ThemeColorStyle.appGray50,
        child: Column(
          children: [
            SizedBox(height: AppDimensions.height15),
            Icon(
              Icons.check_circle,
              color: ThemeColorStyle.appLightGreen,
              size: AppDimensions.fontSize40,
            ),
            SizedBox(height: AppDimensions.height10),
            TextMedium(
              text: 'Payment Successful',
              size: AppDimensions.fontSize20,
              weight: FontWeight.w600,
            ),
            Container(
              padding: EdgeInsets.only(bottom: AppDimensions.height30),
              margin: EdgeInsets.only(top: AppDimensions.height15, left: AppDimensions.width25, right: AppDimensions.height25),
              child: Column(
                children: [
                  const Divider(
                    height: 1,
                    color: ThemeColorStyle.appGray50,
                  ),
                  SizedBox(height: AppDimensions.height15),
                  getDateAndTimeTitleRow(),
                  getDateAndTimeValueRow(),
                  SizedBox(height: AppDimensions.height8),
                  getTitleAndValueRow(
                    title: 'Payer Name:',
                    space: AppDimensions.height8,
                    value: '${transaction.payerDetail?.fullName}',
                  ),
                  getTitleAndValueRow(
                    title: 'Payer Phone:',
                    space: AppDimensions.height8,
                    value: '${transaction.payerDetail?.phoneNumber}',
                  ),
                  getTitleAndValueRow(
                    title: 'Serial #:',
                    space: AppDimensions.height8,
                    value: '${transaction.token}',
                  ),
                  getTitleAndValueRow(
                    title: 'Reference #:',
                    space: AppDimensions.height8,
                    value: '${transaction.reference}',
                  ),
                  canShowBillNumber(),
                  SizedBox(height: AppDimensions.height8),
                  getTitleAndValueRow(
                    title: 'Amount Paid:',
                    space: AppDimensions.height15,
                    value: '${transaction.amount}',
                    size: AppDimensions.fontSize18,
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget getDateAndTimeTitleRow() {
    return Row(
      children: [
        TextMedium(
          text: 'Date',
          size: AppDimensions.fontSize16,
          weight: FontWeight.w600,
        ),
        Expanded(
          child: TextMedium(
            align: TextAlign.end,
            text: 'Time',
            size: AppDimensions.fontSize16,
            weight: FontWeight.w600,
          ),
        )
      ],
    );
  }

  Widget getDateAndTimeValueRow() {
    return Row(
      children: [
        TextMedium(
          text: transaction.date ?? '',
          size: AppDimensions.fontSize14,
          weight: FontWeight.w400,
        ),
        Expanded(
          child: TextMedium(
            align: TextAlign.end,
            text: transaction.time!,
            size: AppDimensions.fontSize14,
            weight: FontWeight.w400,
          ),
        )
      ],
    );
  }

  Widget canShowBillNumber() {
    if (transaction.paymentDetail != null && transaction.paymentDetail?.billNumber != null) {
      return getTitleAndValueRow(
        title: 'Bill Number:',
        space: AppDimensions.height8,
        value: '${transaction.paymentDetail?.billNumber}',
      );
    } else {
      return Container();
    }
  }

  Widget getTitleAndValueRow({required String title, required String value, required double space, double? size}) {
    return Column(
      children: [
        SizedBox(height: space),
        Row(
          children: [
            TextMedium(
              text: title,
              size: size ?? AppDimensions.fontSize14,
              weight: FontWeight.w600,
            ),
            Expanded(
              child: TextMedium(
                align: TextAlign.end,
                text: value,
                size: size ?? AppDimensions.fontSize14,
                weight: FontWeight.w600,
              ),
            )
          ],
        ),
      ],
    );
  }
}
