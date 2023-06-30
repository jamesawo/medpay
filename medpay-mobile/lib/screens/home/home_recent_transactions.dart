import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:medpay/controllers/recent_transaction_controller.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/widgets/empty_state_widget.dart';
import 'package:medpay/widgets/recent_transaction.dart';

class HomeRecentTransactions extends StatefulWidget {
  const HomeRecentTransactions({Key? key}) : super(key: key);

  @override
  State<HomeRecentTransactions> createState() => _HomeRecentTransactionsState();
}

class _HomeRecentTransactionsState extends State<HomeRecentTransactions> {
  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return GetBuilder<RecentTransactionController>(builder: (controller) {
      return controller.hasTransactions
          ? Column(
              children: controller.recentTransactions
                  .map(
                    (entry) => RecentTransaction(
                      date: entry.date,
                      transactions: entry.transactions,
                    ),
                  )
                  .toList(),
            )
          : getNoRecentTransaction();
    });
  }

  Widget getNoRecentTransaction() {
    return SizedBox(
      height: AppDimensions.height240,
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: const [
          EmptyStateWidget(message: 'No transactions yet'),
        ],
      ),
    );
  }
}
