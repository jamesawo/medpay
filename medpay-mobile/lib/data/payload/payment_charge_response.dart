import 'package:medpay/data/enums/app_enum.dart';

class PaymentChargeResponse {
  String? statusText;
  SuccessOrErrorEnum? status;
  String? transactionId;
  String? transactionReference;

  PaymentChargeResponse({
    this.statusText,
    this.status,
    this.transactionId,
    this.transactionReference,
  });
}
