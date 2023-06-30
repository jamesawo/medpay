import 'package:flutter/material.dart';
import 'package:medpay/data/models/bill_model.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/style_utils.dart';
import 'package:medpay/widgets/text_small.dart';

class BillItemTable extends StatelessWidget {
  final List<BillItem>? items;

  const BillItemTable({required this.items, Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: EdgeInsets.symmetric(horizontal: AppDimensions.width25),
      padding: const EdgeInsets.all(0),
      child: Table(
        columnWidths: Map.from({
          0: const FlexColumnWidth(5),
          1: const FlexColumnWidth(1),
          2: const FlexColumnWidth(2),
        }),
        textBaseline: TextBaseline.alphabetic,
        children: [
          // table header
          getTableHeader(),
          if (items != null)
            for (var e in items!) getTableRow(e)
        ],
      ),
    );
  }

  TableRow getTableHeader() {
    return TableRow(
      children: [
        TableCell(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Padding(
                padding: EdgeInsets.only(bottom: AppDimensions.height2),
                child: TextSmall(text: 'Description', size: AppDimensions.fontSize16),
              ),
              Divider(height: 2, color: ThemeColorStyle.appGray30),
              SizedBox(height: AppDimensions.height5),
            ],
          ),
          verticalAlignment: TableCellVerticalAlignment.middle,
        ),
        TableCell(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              Padding(
                padding: EdgeInsets.only(bottom: AppDimensions.height2),
                child: TextSmall(
                  text: 'Qty',
                  size: AppDimensions.fontSize16,
                ),
              ),
              Divider(height: 2, color: ThemeColorStyle.appGray30),
              SizedBox(height: AppDimensions.height5),
            ],
          ),
          verticalAlignment: TableCellVerticalAlignment.middle,
        ),
        TableCell(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              Padding(
                padding: EdgeInsets.only(bottom: AppDimensions.height2),
                child: TextSmall(
                  text: 'Amt',
                  size: AppDimensions.fontSize16,
                ),
              ),
              Divider(height: 2, color: ThemeColorStyle.appGray30),
              SizedBox(height: AppDimensions.height5),
            ],
          ),
          verticalAlignment: TableCellVerticalAlignment.middle,
        ),
      ],
    );
  }

  TableRow getTableRow(BillItem bill) {
    return TableRow(
      children: [
        TableCell(
          verticalAlignment: TableCellVerticalAlignment.middle,
          child: Padding(
            padding: EdgeInsets.only(bottom: AppDimensions.height5),
            child: TextSmall(
              text: '${bill.description}',
              size: AppDimensions.fontSize13,
            ),
          ),
        ),
        TableCell(
          verticalAlignment: TableCellVerticalAlignment.middle,
          child: Padding(
            padding: EdgeInsets.only(bottom: AppDimensions.height5),
            child: TextSmall(
              text: '${bill.quantity}',
              size: AppDimensions.fontSize13,
              align: TextAlign.center,
            ),
          ),
        ),
        TableCell(
          verticalAlignment: TableCellVerticalAlignment.middle,
          child: Padding(
            padding: EdgeInsets.only(bottom: AppDimensions.height5),
            child: TextSmall(
              text: '${bill.amount}',
              size: AppDimensions.fontSize13,
              align: TextAlign.center,
            ),
          ),
        ),
      ],
    );
  }
}
