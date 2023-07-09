class RouteConstants {
  // routes uri constants
  static const String appContentType = 'application/json';

  // auth
  //todo:: handle cases where user is unable to connect due to network failure, return proper error message
  // static const String appBaseURL = 'http://192.168.0.159:9090/api/v1/';
  // static const String appBaseURL = 'https://paymed-m-api.herokuapp.com/api/v1/';
  static const String appBaseURL = 'http://localhost:9090/api/v1/';
  static const String uriRegister = appBaseURL + 'auth/register';
  static const String uriLogin = appBaseURL + 'auth/login';

  static const String uriGETHospitals = appBaseURL + 'hospital/all';
  static const String uriGETHospital = appBaseURL + 'hospital';
  static const String uriGetHospitalSearchBill = appBaseURL + 'hospital/search-bill';
  static const String uriSearchHospitalService = appBaseURL + 'hospital-service/search-by-title';

  //users
  static const String uriGETUser = appBaseURL + 'users/get/';

  // transaction
  static const String uriSearchBillNumber = appBaseURL + 'hospital-api-usage/bill';  // searching billNumber from 3rd part api
  static const String uriPostServicePayment = appBaseURL + 'transaction/create';

  static const String uriSaveTransaction = appBaseURL + 'transaction/pay-bill';
  static const String uriGetTransactions = appBaseURL + 'transaction/recent-by-hospital-and-user';

  // invoice
  static const String uriSearchInvoiceNumber = appBaseURL + 'billing/search'; // searching app billNumber

  // gateway
  static const String uriGenerateRRR = appBaseURL + 'gateway/generate-rrr';

}
