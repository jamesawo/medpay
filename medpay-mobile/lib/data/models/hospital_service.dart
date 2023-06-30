class HospitalService {
  num id;
  String title;
  String code;
  bool isEnabled;

  HospitalService({
    required this.id,
    required this.title,
    required this.code,
    required this.isEnabled,
  });

  factory HospitalService.fromJson(Map<String, dynamic> json) {
    return HospitalService(
      id: json["id"],
      title: json["title"] ?? '',
      code: json["code"] ?? '',
      isEnabled: json["isEnabled"] ?? false,
    );
  }

  Map<String, dynamic> toJson() => {
        'id': id,
        'title': title,
        'code': code,
        'isEnabled': isEnabled,
      };
}
