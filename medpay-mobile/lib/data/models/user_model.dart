import 'package:medpay/data/enums/app_enum.dart';
import 'package:medpay/data/payload/login_response.dart';

class UserModel {
  num? id;
  String? uuid;
  String? username;
  bool? isEnabled;
  String? name;
  String? phone;
  AccountTypeEnum? type;
  String? token;
  String? nickName;

  UserModel({
    this.id,
    this.uuid,
    this.username,
    this.isEnabled,
    this.name,
    this.phone,
    this.type,
    this.token,
    this.nickName,
  });

  factory UserModel.fromLoginResponse(LoginResponse loginResponse) {
    return UserModel(
      username: loginResponse.username,
      uuid: loginResponse.uuid,
      token: loginResponse.token,
      type: loginResponse.accountType,
    );
  }

  factory UserModel.fromJson(Map<String, dynamic> json) {
    return UserModel(
      id: json['id'],
      uuid: json['uuid'],
      username: json['username'],
      name: json['fullName'],
      token: json['token'],
      isEnabled: json['enabled'],
      type: json['type'] != null ? AccountTypeEnum.values.byName(json['type']) : null,
      phone: json['phone'],
      nickName: json['nickName'],
    );
  }

  bool isEmpty() {
    return name == null && username == null && uuid == null;
  }

  Map<String, dynamic> toJson() => {
        'id': id,
        'uuid': uuid,
        'username': username,
        'isEnabled': isEnabled,
        'name': name,
        'phone': phone,
        'type': type?.name,
        'token': token,
        'nickName': nickName,
      };
}
