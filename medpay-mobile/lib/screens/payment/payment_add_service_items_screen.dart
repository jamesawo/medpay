import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:get/get.dart';
import 'package:medpay/controllers/hospital_controller.dart';
import 'package:medpay/controllers/payment_controller.dart';
import 'package:medpay/data/constants/exception_constants.dart';
import 'package:medpay/data/models/hospital_service.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/app_routes.dart';
import 'package:medpay/utils/helper_utils.dart';
import 'package:medpay/utils/style_utils.dart';
import 'package:medpay/widgets/app_button_loading.dart';
import 'package:medpay/widgets/app_top_title.dart';
import 'package:medpay/widgets/search_autocomplete_input_with_button.dart';
import 'package:medpay/widgets/service_items_table.dart';
import 'package:medpay/widgets/text_small.dart';

class PaymentAddServiceItemsScreen extends StatefulWidget {
  final HospitalService? service;
  final TextEditingController? textController;
  const PaymentAddServiceItemsScreen({this.service, this.textController, Key? key}) : super(key: key);

  @override
  State<PaymentAddServiceItemsScreen> createState() => _PaymentAddServiceItemsScreenState();
}

class _PaymentAddServiceItemsScreenState extends State<PaymentAddServiceItemsScreen> {
  @override
  void initState() {
    super.initState();
  }

  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return GetBuilder<HospitalController>(builder: (controller) {
      return AnnotatedRegion<SystemUiOverlayStyle>(
        value: SystemUiOverlayStyle.dark,
        child: Scaffold(
          resizeToAvoidBottomInset: true,
          backgroundColor: ThemeColorStyle.appWhite,
          body: SafeArea(
            child: getBody(controller),
          ),
          bottomNavigationBar: getBottomNavigationBottom(controller),
        ),
      );
    });
  }

  Widget getBody(HospitalController _hospitalController) {
    return Column(
      children: [
        AppTopTitle(
          title: 'Add Service Items',
          action: () => onNavigateBackToHome(),
        ),
        Container(
          margin: EdgeInsets.only(left: AppDimensions.width20, right: AppDimensions.width20),
          child: SearchAutoCompleteInputWithButton(
            textController: widget.textController,
            hintText: 'Search for service ...',
            isItemsScreen: true,
          ),
        ),
        SizedBox(height: AppDimensions.height20),
        // bill items table
        const ServiceItemsWidget(),
        // bill amount detail
        SizedBox(height: AppDimensions.height30),
      ],
    );
  }

  Widget getBottomNavigationBottom(HospitalController controller) {
    return Container(
      margin: EdgeInsets.symmetric(horizontal: AppDimensions.width25, vertical: AppDimensions.height20),
      padding: const EdgeInsets.all(0),
      child: Get.find<PaymentController>().isMakingPayment == true
          ? const AppButtonLoading()
          : ElevatedButton(
              style: ThemeInputStyle.primaryButtonStyle,
              child: TextSmall(
                text: 'Proceed',
                size: AppDimensions.fontSize15,
                color: ThemeColorStyle.appWhite,
                weight: FontWeight.w600,
              ),
              onPressed: () {
                // proceed
                if (controller.services.isEmpty) {
                  HelperUtils.showErrorSnackBar(
                    title: ExceptionConstants.errorFriendlyTitle,
                    message: ExceptionConstants.errorEmptyServiceList,
                  );
                  return;
                } else {
                  HelperUtils.navigateTo(
                    AppRoutes.paymentPayerDetailScreen,
                  );
                }
              },
            ),
    );
  }

  void onNavigateBackToHome() {
    Get.find<HospitalController>().clearSelectedServices();
    HelperUtils.navigateToMainAndRemoveAll();
  }
}
