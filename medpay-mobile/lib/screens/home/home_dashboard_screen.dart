import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:get/get.dart';
import 'package:medpay/controllers/auth_controller.dart';
import 'package:medpay/controllers/hospital_controller.dart';
import 'package:medpay/controllers/recent_transaction_controller.dart';
import 'package:medpay/screens/home/home_recent_transactions.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/app_routes.dart';
import 'package:medpay/utils/helper_utils.dart';
import 'package:medpay/utils/style_utils.dart';
import 'package:medpay/widgets/bill_or_service_search.dart';
import 'package:medpay/widgets/hero_home.dart';
import 'package:medpay/widgets/home_action_card.dart';
import 'package:medpay/widgets/text_medium.dart';
import 'package:medpay/widgets/text_small.dart';

class HomeDashboardScreen extends StatefulWidget {
  const HomeDashboardScreen({Key? key}) : super(key: key);

  @override
  State<HomeDashboardScreen> createState() => _HomeDashboardScreenState();
}

class _HomeDashboardScreenState extends State<HomeDashboardScreen> {
  late TextEditingController _textController;
  late AuthController _authController;
  late RecentTransactionController _recentTransactionController;

  @override
  void initState() {
    WidgetsBinding.instance.addPostFrameCallback((_) => onClearServices());
    super.initState();
    _authController = Get.find<AuthController>();
    _recentTransactionController = Get.find<RecentTransactionController>();
    _textController = TextEditingController();
    _textController.text = '';
  }

  void onClearServices() {
    Get.find<HospitalController>().clearSelectedServices();
  }

  @override
  void dispose() {
    _textController.dispose();
    super.dispose();
  }

  Future<void> onRefreshData() async {
    Get.find<RecentTransactionController>().getRecentTransactionsFromApi();
  }

  @override
  Widget build(BuildContext context) {
    return GetBuilder<RecentTransactionController>(builder: (_) {
      return AnnotatedRegion<SystemUiOverlayStyle>(
        value: SystemUiOverlayStyle.light,
        child: RefreshIndicator(
          onRefresh: () => onRefreshData(),
          child: Scaffold(
            resizeToAvoidBottomInset: true,
            backgroundColor: ThemeColorStyle.appWhite,
            body: CustomScrollView(
              scrollDirection: Axis.vertical,
              reverse: false,
              slivers: [
                getSliverAppBar(),
                getSliverBoxAdapter(),
              ],
            ),
          ),
        ),
      );
    });
  }

  SliverToBoxAdapter getSliverBoxAdapter() {
    return SliverToBoxAdapter(
      child: Column(
        children: [
          Column(
            children: [
              getTopWidgets(),
              _recentTransactionController.isLoading == true ? HelperUtils.getSpinnerWidget() : getRecentTransactions(),
            ],
          ),
        ],
      ),
    );
  }

  SliverAppBar getSliverAppBar() {
    var username = _authController.loggedInUser.nickName ?? '';
    return SliverAppBar(
      titleSpacing: 0,
      backgroundColor: ThemeColorStyle.appBlue,
      automaticallyImplyLeading: false,
      expandedHeight: AppDimensions.height120,
      flexibleSpace: FlexibleSpaceBar(
        background: HeroHome(name: username),
      ),
      pinned: true,
      floating: true,
      stretch: false,
      collapsedHeight: AppDimensions.height50,
      toolbarHeight: AppDimensions.height50,
    );
  }

  Widget getTopWidgets() {
    return Container(
      margin: EdgeInsets.only(left: AppDimensions.width20, right: AppDimensions.width20),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          SizedBox(height: AppDimensions.height15),
          const BillOrServiceSearchInput(),
          SizedBox(height: AppDimensions.height20),
          getActionCard(),
          SizedBox(height: AppDimensions.height20),
          getRecentTransactionsTitle(),
        ],
      ),
    );
  }

  Widget getActionCard() {
    return Row(
      children: [
        HomeActionCard(
          text: 'Generate \n Statement',
          icon: Icons.receipt_long,
          action: () => HelperUtils.navigateTo(AppRoutes.transactionStatementScreen),
        ),
        HomeActionCard(
          text: 'View \n Transactions',
          icon: Icons.history_edu_outlined,
          action: () => HelperUtils.navigateTo(AppRoutes.transactionHistoryScreen),
        ),
        HomeActionCard(
          text: 'Verify \n Payments',
          icon: Icons.fact_check_outlined,
          action: () => HelperUtils.navigateTo(AppRoutes.paymentVerificationScreen),
        ),
      ],
    );
  }

  Widget getRecentTransactionsTitle() {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: [
        TextMedium(
          text: 'Recent Transactions',
          weight: FontWeight.w600,
          size: AppDimensions.fontSize16,
        ),
        SizedBox(
          child: Row(crossAxisAlignment: CrossAxisAlignment.end, children: [
            TextSmall(
              size: AppDimensions.fontSize12,
              text: 'Last 5days',
              color: ThemeColorStyle.appGray50,
              weight: FontWeight.w500,
            ),
            SizedBox(width: AppDimensions.width3),
            Icon(
              Icons.error_outline_outlined,
              size: AppDimensions.height10,
              color: ThemeColorStyle.appBlue,
            )
          ]),
        ),
      ],
    );
  }

  Widget getRecentTransactions() {
    return Column(
      children: [
        SizedBox(height: AppDimensions.height10),
        const HomeRecentTransactions(),
      ],
    );
  }
}
