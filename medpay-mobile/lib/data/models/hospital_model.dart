import 'package:medpay/data/enums/app_enum.dart';

class HospitalModel {
  String? id;
  String? title;
  String? logoUrl;
  String? address;
  bool? useHospitalSoftware;
  CollectionModeEnum? collectionModelEnum;
  String? supportName;
  String? supportPhone;
  String? supportEmail;
  SupportChannelEnum? supportChannel;

  HospitalModel({
    this.id,
    this.title,
    this.logoUrl,
    this.address,
    this.useHospitalSoftware,
    this.collectionModelEnum,
    this.supportName,
    this.supportPhone,
    this.supportEmail,
    this.supportChannel,
  });

  HospitalModel.fromJson(Map<String, dynamic> json) {
    id = json['id'].toString();
    useHospitalSoftware = json['hasHospitalSoftware'];
    collectionModelEnum =
        json['collectionModelEnum'] != null ? CollectionModeEnum.values.byName(json['collectionModelEnum']) : null;
    var basicDetails = json['detail'];
    if (basicDetails != null) {
      address = basicDetails['hospitalAddress'];
      logoUrl = basicDetails['hospitalLogoUrl'];
      title = basicDetails['name'];
      supportName = basicDetails['supportName'];
      supportPhone = basicDetails['supportPhone'];
      supportEmail = basicDetails['supportEmail'];
      if (basicDetails['supportChannel'] != null) {
        supportChannel = SupportChannelEnum.values.byName(basicDetails['supportChannel']);
      }
    }
  }

  Map toJson() => {
        'id': id,
        'title': title,
        'logoUrl': logoUrl,
        'address': address,
        'useHospitalSoftware': useHospitalSoftware,
        'collectionModelEnum': collectionModelEnum != null ? collectionModelEnum!.name : null,
        'supportName': supportName,
        'supportPhone': supportPhone,
        'supportEmail': supportEmail,
        'supportChannel': supportChannel != null ? supportChannel!.name : null,
      };
}
