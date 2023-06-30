import 'package:flutter/material.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/style_utils.dart';
import 'package:medpay/widgets/app_top_title.dart';
import 'package:medpay/widgets/form_input_with_label.dart';
import 'package:medpay/widgets/text_small.dart';

class TransactionStatementScreen extends StatefulWidget {
  const TransactionStatementScreen({Key? key}) : super(key: key);

  @override
  State<TransactionStatementScreen> createState() => _TransactionStatementScreenState();
}

class _TransactionStatementScreenState extends State<TransactionStatementScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: ThemeColorStyle.appWhite,
      body: SafeArea(
        child: CustomScrollView(
          slivers: [
            SliverAppBar(
              titleSpacing: 0,
              automaticallyImplyLeading: false,
              backgroundColor: ThemeColorStyle.appWhite,
              pinned: true,
              floating: true,
              stretch: false,
              title: const AppTopTitle(
                title: 'Generate \n Transaction Statement',
                showMoreVert: false,
              ),
              toolbarHeight: AppDimensions.height50,
            ),
            SliverToBoxAdapter(
              child: Column(
                children: [
                  Container(
                    padding: EdgeInsets.only(left: AppDimensions.width35),
                    margin: EdgeInsets.symmetric(
                      vertical: AppDimensions.height30,
                    ),
                    width: double.maxFinite,
                    height: AppDimensions.height25,
                    color: ThemeColorStyle.appGray20,
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        Row(
                          children: [
                            const Icon(Icons.info, color: ThemeColorStyle.appBlue),
                            SizedBox(width: AppDimensions.width15),
                            Expanded(
                              child: TextSmall(
                                align: TextAlign.left,
                                size: AppDimensions.fontSize12,
                                text: 'Statement will be sent to your registered email',
                                weight: FontWeight.w500,
                                color: ThemeColorStyle.appGray50,
                              ),
                            ),
                          ],
                        ),
                      ],
                    ),
                  ),
                  Container(
                    margin: EdgeInsets.symmetric(
                      vertical: AppDimensions.height30,
                      horizontal: AppDimensions.width30,
                    ),
                    child: Column(
                      children: [
                        FormInputWithLabel(
                          controller: TextEditingController(),
                          label: 'Start Date',
                          inputHintText: 'DD/MM/YYYY',
                          suffixIcon: Icons.calendar_today,
                        ),
                        SizedBox(height: AppDimensions.height20),
                        FormInputWithLabel(
                          controller: TextEditingController(),
                          label: 'End Date',
                          inputHintText: 'DD/MM/YYYY',
                          suffixIcon: Icons.calendar_today,
                        ),
                        SizedBox(height: AppDimensions.height30),
                        SizedBox(
                          width: double.infinity,
                          child: ElevatedButton(
                            style: ThemeInputStyle.primaryButtonStyle,
                            child: TextSmall(
                              text: 'Proceed',
                              size: AppDimensions.fontSize16,
                              color: ThemeColorStyle.appWhite,
                              weight: FontWeight.w600,
                            ),
                            onPressed: () {},
                          ),
                        ),
                      ],
                    ),
                  )
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
