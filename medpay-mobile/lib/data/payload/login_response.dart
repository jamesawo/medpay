import 'package:medpay/data/enums/app_enum.dart';

class LoginResponse {
  AccountTypeEnum? accountType;
  String? username;
  String? token;
  String? uuid;

  LoginResponse({this.accountType, this.username, this.token, this.uuid});

  factory LoginResponse.fromJson(Map<String, dynamic> json) {
    return LoginResponse(
      username: json['username'],
      token: json['token'],
      accountType: AccountTypeEnum.values.byName(json['type'] ?? ''),
      uuid: json['uuid'],
    );
  }
}
