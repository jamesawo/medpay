import 'package:flutter/material.dart';
import 'package:medpay/screens/home/home_recent_transactions.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/style_utils.dart';
import 'package:medpay/widgets/app_top_title.dart';
import 'package:medpay/widgets/empty_state_widget.dart';
import 'package:medpay/widgets/search_input_with_button.dart';

class TransactionHistoryScreen extends StatefulWidget {
  const TransactionHistoryScreen({Key? key}) : super(key: key);

  @override
  State<TransactionHistoryScreen> createState() => _TransactionHistoryScreenState();
}

class _TransactionHistoryScreenState extends State<TransactionHistoryScreen> {
  final bool _hasRecentTransaction = true;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: ThemeColorStyle.appWhite,
      body: SafeArea(
        child: CustomScrollView(
          slivers: [
            const SliverAppBar(
              titleSpacing: 0,
              automaticallyImplyLeading: false,
              backgroundColor: ThemeColorStyle.appWhite,
              pinned: true,
              floating: true,
              stretch: false,
              title: AppTopTitle(
                title: 'Transaction History',
                showMoreVert: true,
              ),
            ),
            SliverToBoxAdapter(
              child: Column(
                children: [
                  Container(
                    margin: EdgeInsets.symmetric(
                      vertical: AppDimensions.width30,
                      horizontal: AppDimensions.width25,
                    ),
                    child: const SearchInputWithButton(searchText: ''),
                  ),
                ],
              ),
            ),
            SliverList(
              delegate: SliverChildListDelegate(
                [
                  _hasRecentTransaction
                      ? const HomeRecentTransactions()
                      : SizedBox(
                          height: AppDimensions.height240,
                          child: Column(
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: const [EmptyStateWidget(message: 'Ops! No Transactions Yet')],
                          ),
                        ),
                ],
              ),
            )
          ],
        ),
      ),
    );
  }
}
