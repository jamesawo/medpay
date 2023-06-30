class RegisterPayload {
  String name;
  String phoneNumber;
  String email;
  String password;

  RegisterPayload({
    required this.name,
    required this.phoneNumber,
    required this.email,
    required this.password,
  });

  factory RegisterPayload.unnamed(String name, String phone, String email, String password) {
    return RegisterPayload(
      name: name,
      phoneNumber: phone,
      email: email,
      password: password,
    );
  }

  Map<String, dynamic> toJson() {
    Map<String, dynamic> data = <String, dynamic>{};
    data['name'] = name;
    data['phoneNumber'] = phoneNumber;
    data['email'] = email;
    data['password'] = password;
    return data;
  }
}
