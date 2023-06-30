import 'dart:convert';

import 'package:get/get.dart';
import 'package:intl/intl.dart';
import 'package:medpay/data/models/recent_transaction_model.dart';
import 'package:medpay/data/models/transaction_model.dart';
import 'package:medpay/repositories/recent_transaction_repository.dart';
import 'package:medpay/utils/logger_utils.dart';

class RecentTransactionController extends GetxController {
  final RecentTransactionRepository repository;
  RecentTransactionController({required this.repository});

  List<RecentTransactionModel> _recentTransactions = [];
  List<RecentTransactionModel> get recentTransactions => _recentTransactions;

  bool get hasTransactions => _recentTransactions.isNotEmpty;

  bool _isLoading = false;
  bool get isLoading => _isLoading;
  set isLoading(bool value) {
    _isLoading = value;
    update();
  }

  void getRecentTransactionsFromApi() async {
    try {
      isLoading = true;
      Response response = await repository.onGetRecentTransactions();
      if (response.statusCode == 200) {
        var payload = jsonDecode(jsonEncode(response.body));
        if (payload != null) {
          List<RecentTransactionModel> list =
              List<RecentTransactionModel>.from(payload.map((e) => RecentTransactionModel.fromJson(e)));
          _recentTransactions = [];
          _recentTransactions.addAll(list);
        }
        isLoading = false;
        return;
      }
      isLoading = false;
      return;
    } catch (e) {
      isLoading = false;
      LoggerUtils.logError(error: e, caller: '${this}.toString() ${getRecentTransactionsFromApi.toString()}');
    }
  }

  void addTransaction(TransactionModel trans) {
    try {
      isLoading = true;
      String date = DateFormat('yMMMd').format(DateTime.now());
      if (hasTransactions) {
        var index = _recentTransactions.indexWhere((element) => element.date == date);
        if (index != -1) {
          var selectedTransactions = _recentTransactions[index].transactions;
          selectedTransactions.insert(0, trans);
        }
      } else {
        _recentTransactions.add(RecentTransactionModel(
          date: date,
          transactions: [trans],
        ));
      }
      isLoading = false;
    } catch (e) {
      isLoading = false;
      LoggerUtils.logError(error: e, caller: '${this}.toString() ${getRecentTransactionsFromApi.toString()}');
    }
  }

  void clearTransactions() {
    _recentTransactions.clear();
  }
}
