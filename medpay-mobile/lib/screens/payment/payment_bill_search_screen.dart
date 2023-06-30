import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:get/get.dart';
import 'package:medpay/controllers/payment_controller.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/style_utils.dart';
import 'package:medpay/widgets/app_center_loading.dart';
import 'package:medpay/widgets/app_top_title.dart';
import 'package:medpay/widgets/bill_search_card.dart';
import 'package:medpay/widgets/empty_state_widget.dart';
import 'package:medpay/widgets/search_input_with_button.dart';

class PaymentBillSearchScreen extends StatefulWidget {
  final String? searchTerm;
  const PaymentBillSearchScreen({this.searchTerm, Key? key}) : super(key: key);

  @override
  State<PaymentBillSearchScreen> createState() => _PaymentBillSearchScreenState();
}

class _PaymentBillSearchScreenState extends State<PaymentBillSearchScreen> {
  late TextEditingController textController;
  PaymentController paymentController = Get.find<PaymentController>();

  @override
  void initState() {
    super.initState();
    textController = TextEditingController();
    var searchTerm = widget.searchTerm;
    textController.text = searchTerm ?? '';

    if (searchTerm != null && searchTerm.isNotEmpty) {
      paymentController.onSearchBillDetails(searchTerm);
    }
  }

  @override
  void dispose() {
    textController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return GetBuilder<PaymentController>(builder: (controller) {
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
                  const AppTopTitle(title: 'Search Hospital Bill'),
                  SizedBox(height: AppDimensions.height10),
                  Container(
                    margin: EdgeInsets.only(left: AppDimensions.width25, right: AppDimensions.width25),
                    child: SearchInputWithButton(
                      searchText: textController.text,
                      textController: textController,
                      action: () {
                        paymentController.onSearchBillDetails(textController.text);
                      },
                    ),
                  ),
                  SizedBox(height: AppDimensions.height20),
                  controller.isSearching == true
                      ? SizedBox(height: AppDimensions.height300, child: const AppPageLoading())
                      : (controller.searchResult.isNotEmpty
                          ? Container(
                              margin: EdgeInsets.only(left: AppDimensions.width25, right: AppDimensions.width25),
                              child: Column(
                                children: controller.searchResult.map<Widget>((e) => BillSearchCard(bill: e)).toList(),
                              ),
                            )
                          : SizedBox(
                              height: AppDimensions.height300,
                              child: Column(
                                mainAxisAlignment: MainAxisAlignment.center,
                                children: const [
                                  EmptyStateWidget(
                                    message: 'No search result yet',
                                    style: ImageStyle.style2,
                                  ),
                                ],
                              ),
                            )),
                ],
              ),
            ),
          ),
        ),
      );
    });
  }
}
