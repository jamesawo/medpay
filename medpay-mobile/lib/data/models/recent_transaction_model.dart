import 'package:medpay/data/models/transaction_model.dart';

class RecentTransactionModel {
  final String date;
  final List<TransactionModel> transactions;

  RecentTransactionModel({required this.date, required this.transactions});

  factory RecentTransactionModel.fromJson(Map json) {
    List<dynamic> list = json['transactions'] ?? [];
    return RecentTransactionModel(
      date: json['date'],
      transactions: list.map((e) => TransactionModel.fromJson(e)).toList(),
    );
  }
}
