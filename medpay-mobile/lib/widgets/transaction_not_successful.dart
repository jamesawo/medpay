import 'package:flutter/material.dart';
import 'package:medpay/data/enums/app_enum.dart';
import 'package:medpay/data/models/transaction_model.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/style_utils.dart';
import 'package:medpay/widgets/text_medium.dart';

class TransactionNotSuccessful extends StatelessWidget {
  final TransactionModel transaction;

  const TransactionNotSuccessful({required this.transaction, Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    var statusMessage = setMessageText();
    return Container(
      margin: EdgeInsets.only(left: AppDimensions.width30, right: AppDimensions.width30),
      child: Card(
        elevation: 4,
        shadowColor: ThemeColorStyle.appGray50,
        child: Column(
          children: [
            SizedBox(height: AppDimensions.height15),
            Icon(
              statusMessage.iconData,
              color: ThemeColorStyle.appRed,
              size: AppDimensions.fontSize40,
            ),
            SizedBox(height: AppDimensions.height10),
            TextMedium(
              text: statusMessage.header,
              size: AppDimensions.fontSize20,
              weight: FontWeight.w600,
            ),
            Container(
              padding: EdgeInsets.only(bottom: AppDimensions.height30),
              margin: EdgeInsets.only(top: AppDimensions.height15, left: AppDimensions.width25, right: AppDimensions.height25),
              child: Column(
                children: [
                  getMessageContent(statusMessage),
                  SizedBox(height: AppDimensions.height8),
                  getDateAndTimeTitleRow(),
                  getDateAndTimeValueRow(),
                  getTitleAndValueRow(
                    title: 'Payer Name:',
                    space: AppDimensions.height8,
                    value: '${transaction.payerDetail?.fullName}',
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
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget getMessageContent(TransactionStatusMessage statusMessage) {
    return Column(
      children: [
        const Divider(height: 1, color: ThemeColorStyle.appGray50),
        SizedBox(height: AppDimensions.height15),
        Column(
          children: [
            TextMedium(
              text: statusMessage.title,
              color: ThemeColorStyle.appBlack,
              size: AppDimensions.fontSize16,
              weight: FontWeight.w500,
            ),
            SizedBox(height: AppDimensions.height10),
            TextMedium(
              text: statusMessage.subTitle,
              color: ThemeColorStyle.appBlack,
              align: TextAlign.center,
              size: AppDimensions.fontSize16,
              weight: FontWeight.w500,
              overflow: TextOverflow.visible,
            )
          ],
        ),
      ],
    );
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

  TransactionStatusMessage setMessageText() {
    String title = '';
    String subTitle = '';
    String header = '';
    IconData iconData;
    var message = transaction.statusEnum;
    switch (message) {
      case TransactionStatusEnum.CANCELLED:
        iconData = Icons.cancel;
        header = 'Payment Cancelled';
        title = "Oh No!";
        subTitle = 'Payment has been cancelled.';
        break;
      case TransactionStatusEnum.INITIATED:
        iconData = Icons.info;
        header = 'Payment In Progress';
        title = 'Hold on a bit';
        subTitle = 'Payment is in progress, please wait.';
        break;
      case TransactionStatusEnum.PENDING:
        iconData = Icons.upload_outlined;
        header = 'Payment Pending';
        title = 'It is pending';
        subTitle = 'Payment is currently pending, please wait.';
        break;
      case TransactionStatusEnum.SUCCESSFUL:
        iconData = Icons.check_circle;
        header = 'Payment Successful';
        title = 'Yes, success!';
        subTitle = 'Payment is sent successfully';
        break;
      case TransactionStatusEnum.FAILED:
        iconData = Icons.cancel;
        header = 'Payment Failed';
        title = transaction.isBillTransaction() ? 'Please contact support for help.' : 'Please try again later';
        subTitle = transaction.isBillTransaction()
            ? 'Third party connection failed to accept payment'
            : 'Network error, wait and try later';
        break;
      default:
        iconData = Icons.check_circle;
        header = 'Invalid Status';
        title = '';
        subTitle = '';
        break;
    }
    return TransactionStatusMessage(title: title, subTitle: subTitle, header: header, iconData: iconData);
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
}
