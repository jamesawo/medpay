class ResponsePayload<T> {
  String? status;
  String? message;
  int? code;
  T? data;
  String? error;

  ResponsePayload({
    this.status,
    this.message,
    this.code,
    this.data,
    this.error,
  });

  factory ResponsePayload.fromJson(Map<String, dynamic> json) {
    String errorString = '';
    var error = json['error'] ?? [];
    if (error != null && error.isNotEmpty) {
      if (error.runtimeType == String) {
        errorString = error;
      } else if (error.runtimeType == List<dynamic>) {
        errorString = error.join("\n");
      }
    }

    return ResponsePayload(
      status: json['status'].toString(),
      message: json['message'],
      code: json['code'],
      data: json['data'],
      error: errorString,
    );
  }
}
