class RegisterPayload {
  String name;
  String phone;
  String email;
  String password;

  RegisterPayload({
    required this.name,
    required this.phone,
    required this.email,
    required this.password,
  });

  factory RegisterPayload.unnamed(String name, String phone, String email, String password) {
    return RegisterPayload(
      name: name,
      phone: phone,
      email: email,
      password: password,
    );
  }

  Map<String, dynamic> toJson() {
    Map<String, dynamic> data = <String, dynamic>{};
    data['name'] = name;
    data['phone'] = phone;
    data['email'] = email;
    data['password'] = password;
    return data;
  }
}
