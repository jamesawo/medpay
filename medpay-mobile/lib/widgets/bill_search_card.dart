import 'package:flutter/material.dart';
import 'package:medpay/data/models/bill_model.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/app_routes.dart';
import 'package:medpay/utils/helper_utils.dart';
import 'package:medpay/utils/style_utils.dart';
import 'package:medpay/widgets/text_medium.dart';

class BillSearchCard extends StatelessWidget {
  final HospitalBill? bill;

  const BillSearchCard({this.bill, Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: EdgeInsets.only(bottom: AppDimensions.height20),
      child: Card(
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(AppDimensions.height15),
        ),
        elevation: 5,
        child: Container(
          padding: EdgeInsets.symmetric(vertical: AppDimensions.height15, horizontal: AppDimensions.width15),
          child: Column(
            children: [
              Row(
                mainAxisAlignment: MainAxisAlignment.start,
                children: [
                  SizedBox(width: AppDimensions.width5),
                  TextMedium(
                    text: 'BILL SUMMARY',
                    size: AppDimensions.fontSize16,
                    weight: FontWeight.bold,
                  ),
                ],
              ),
              SizedBox(height: AppDimensions.height15),
              Row(
                children: [
                  TextMedium(
                    text: 'Patient Name:',
                    size: AppDimensions.fontSize14,
                  ),
                  Expanded(
                    child: TextMedium(
                      align: TextAlign.end,
                      text: bill?.patient?.fullName ?? '',
                      size: AppDimensions.fontSize14,
                    ),
                  )
                ],
              ),
              SizedBox(height: AppDimensions.height5 + 3),
              Row(
                children: [
                  TextMedium(
                    text: 'Patient Number:',
                    size: AppDimensions.fontSize14,
                  ),
                  Expanded(
                    child: TextMedium(
                      align: TextAlign.end,
                      text: bill?.patient?.patientNumber ?? '',
                      size: AppDimensions.fontSize14,
                    ),
                  )
                ],
              ),
              SizedBox(height: AppDimensions.height5 + 3),
              Row(
                children: [
                  TextMedium(
                    text: 'Invoice Number:',
                    size: AppDimensions.fontSize14,
                  ),
                  Expanded(
                    child: TextMedium(
                      align: TextAlign.end,
                      text: bill?.billNumber ?? '',
                      size: AppDimensions.fontSize14,
                    ),
                  )
                ],
              ),
              SizedBox(height: AppDimensions.height5 + 3),
              Row(
                children: [
                  TextMedium(
                    text: 'Bill Date:',
                    size: AppDimensions.fontSize14,
                  ),
                  Expanded(
                    child: TextMedium(
                      align: TextAlign.end,
                      text: bill?.date ?? '',
                      size: AppDimensions.fontSize14,
                    ),
                  )
                ],
              ),
              SizedBox(height: AppDimensions.height5 + 3),
              Row(
                children: [
                  TextMedium(
                    text: 'Amount Payable',
                    size: AppDimensions.fontSize16,
                    weight: FontWeight.w600,
                  ),
                  Expanded(
                    child: TextMedium(
                      align: TextAlign.end,
                      text: bill?.total?.netAmount ?? '',
                      size: AppDimensions.fontSize20,
                      weight: FontWeight.w600,
                    ),
                  )
                ],
              ),
              Align(
                alignment: AlignmentDirectional.topEnd,
                child: InkWell(
                  onTap: () {
                    HelperUtils.navigateToWithArgs(AppRoutes.paymentBillDetailScreen, bill);
                  },
                  child: Container(
                    padding: EdgeInsets.symmetric(vertical: AppDimensions.height15),
                    // height: 30,
                    child: TextMedium(
                      text: 'View Details',
                      size: AppDimensions.fontSize12,
                      weight: FontWeight.w400,
                      color: ThemeColorStyle.appBlue,
                    ),
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
