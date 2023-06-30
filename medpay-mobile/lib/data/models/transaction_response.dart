import 'package:medpay/data/enums/app_enum.dart';

class TransactionResponse {
  final String reference;
  final String billNumber;
  final String date;
  final String time;
  final SuccessOrErrorEnum? status;
  final String? amount;
  final String? transactionId;

  TransactionResponse({
    required this.reference,
    required this.billNumber,
    required this.date,
    required this.time,
    this.status,
    this.amount,
    this.transactionId,
  });

  factory TransactionResponse.fromJson(Map<String, dynamic>? json) {
    if (json == null) {
      return TransactionResponse.fromEmpty();
    }
    return TransactionResponse(
      reference: '${json['reference']}',
      billNumber: '${json['billNumber']}',
      date: '${json['date']}',
      time: '${json['time']}',
      transactionId: '${json['transactionId']}',
      amount: '${json['amount']}',
      status: SuccessOrErrorEnum.values.byName('${json['status']}'),
    );
  }

  factory TransactionResponse.fromEmpty() {
    return TransactionResponse(
      reference: '',
      billNumber: '',
      date: '',
      time: '',
      status: SuccessOrErrorEnum.error,
      amount: '',
      transactionId: '',
    );
  }
}
