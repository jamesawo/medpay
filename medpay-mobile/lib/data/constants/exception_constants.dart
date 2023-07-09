class ExceptionConstants {
  // generic exception message
  static const String errorDefaultTitle = 'An error occurred.';
  static const String errorDefaultMessage = 'We are working on it, try again in a short while.';
  static const String errorUnAuthorized = 'We could not authorize your request';
  static const String errorFriendlyTitle = "Something isn't right.";
  static const String errorFailed = 'Request Failed';
  static const String errorEmptyBillSearch = 'Please provide the bill number to search';
  static const String errorEmptySearchGeneric = 'Please provide a search value';
  static const String errorEmptyServiceList = 'Please add at least one service first';

  // transaction failed
  static const String errorTransactionFailed = 'Something went wrong. \n Please review and try again';

  // set location
  static const String errorSelectHospital = 'You have to select your hospital.';
  static const String errorSelectHospitalMessage = "Go to hospital setting and choose your current hospital";
}

class MessageConstants {
  static const String verifyEmail = 'Please check your inbox to verify your email';
  static const String paymentSuccess = 'Payment Successful!';
  static const String paymentFailed = 'Payment Failed!';
  static const String paymentCancelled = 'Payment Cancelled!';
  static const String paymentError = 'There was a problem with your last transaction!';
}
