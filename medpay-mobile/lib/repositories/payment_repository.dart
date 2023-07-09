import 'package:get/get.dart';
import 'package:medpay/data/api/api_client.dart';
import 'package:medpay/data/constants/route_constants.dart';
import 'package:medpay/data/models/transaction_model.dart';

class PaymentRepository extends GetxService {
  final ApiClient apiClient;

  PaymentRepository({required this.apiClient});

  Future<Response> doSearchBillDetails(String billNumber, String hospital) async {
    return await apiClient.get(
      RouteConstants.uriSearchBillNumber + '?billNumber=$billNumber&hospitalId=$hospital',
      headers: apiClient.mainHeaders,
    );
  }

  Future<Response> doSearchInvoiceNumberDetails(String invoiceNumber, String hospital) async {
    return await apiClient.get(
      RouteConstants.uriSearchInvoiceNumber + '?term=$invoiceNumber&hospitalId=$hospital',
      headers: apiClient.mainHeaders,
    );
  }

  Future<Response> doTransactionPayment(TransactionModel transaction) async {
    var payload = transaction.toJson();
    return await apiClient.post(
      RouteConstants.uriPostServicePayment,
      payload,
      headers: apiClient.mainHeaders,
    );
  }

  Future<Response> doGenerateRrr(TransactionModel transaction) async {
    var payload = transaction.toJson();
    return await apiClient.post(
      RouteConstants.uriGenerateRRR,
      payload,
      headers: apiClient.mainHeaders,
    );
  }



/* Future<Response> doGenerateRRR(String user, String amount) async {
    return await apiClient.post(
      RouteConstants.uriGenerateRRR + '?user=$user&amount=$amount',
      {},
      headers: apiClient.mainHeaders,
    );
  }*/

  /*Future<Response> doSaveTransaction(Transaction transaction) async {
    return await apiClient.post(
      RouteConstants.uriSaveTransaction,
      jsonEncode(transaction.toJson()),
      headers: apiClient.mainHeaders,
    );
  }*/
}
