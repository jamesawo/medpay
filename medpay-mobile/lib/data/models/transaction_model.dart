import 'package:flutter/material.dart';
import 'package:medpay/data/enums/app_enum.dart';

import 'hospital_model.dart';
import 'hospital_service.dart';
import 'user_model.dart';

class TransactionModel {
  num? id;
  String? uuid;
  String? date;
  String? reference;
  String? token;
  String? time;
  num? amount;
  int? notificationCount;
  bool? hasNotifiedHospital;
  TransactionStatusEnum? statusEnum;
  HospitalModel? hospital;
  UserModel? user;
  TransactionPaymentDetailModel? paymentDetail;
  TransactionPayerDetailModel? payerDetail;
  String? receiptUrl;

  TransactionModel(
      {this.id,
      this.uuid,
      this.date,
      this.reference,
      this.token,
      this.time,
      this.amount,
      this.notificationCount,
      this.hasNotifiedHospital,
      this.statusEnum,
      this.hospital,
      this.user,
      this.paymentDetail,
      this.payerDetail,
      this.receiptUrl});

  factory TransactionModel.fromJson(Map<String, dynamic> json) {
    return TransactionModel(
      id: json['id'],
      uuid: json['uuid'],
      date: json['date'],
      reference: json['reference'],
      token: json['token'],
      time: json['time'],
      amount: json['amount'],
      notificationCount: json['notificationCount'] ?? 0,
      hasNotifiedHospital: json['hasNotifiedHospital'] ?? false,
      statusEnum: TransactionStatusEnum.values.byName(json['statusEnum'] ?? ''),
      hospital: json['hospital'] != null ? HospitalModel.fromJson(json['hospital']) : null,
      user: json['user'] != null ? UserModel.fromJson(json['user']) : null,
      paymentDetail: json['paymentDetail'] != null ? TransactionPaymentDetailModel.fromJson(json['paymentDetail']) : null,
      payerDetail: json['payerDetail'] != null ? TransactionPayerDetailModel.fromJson(json['payerDetail']) : null,
      receiptUrl: json['receiptUrl'],
    );
  }

  Map toJson() => {
        'id': id,
        'uuid': uuid,
        'date': date,
        'reference': reference,
        'token': token,
        'time': time,
        'amount': amount,
        'notificationCount': notificationCount,
        'hasNotifiedHospital': hasNotifiedHospital,
        'statusEnum': statusEnum,
        'hospital': hospital?.toJson(),
        'user': user?.toJson(),
        'paymentDetail': paymentDetail?.toJson(),
        'payerDetail': payerDetail?.toJson(),
        'receiptUrl': receiptUrl
      };

  bool isBillTransaction() {
    return paymentDetail != null && paymentDetail?.billNumber != null && paymentDetail!.billNumber!.isNotEmpty;
  }

  bool isSuccessfulTransaction() {
    return statusEnum == TransactionStatusEnum.SUCCESSFUL;
  }
}

class TransactionPaymentDetailModel {
  num? id;
  String? billNumber;
  HospitalServiceCategoryEnum? category;
  List<HospitalService>? services;

  TransactionPaymentDetailModel({
    this.id,
    this.billNumber,
    this.category,
    this.services,
  });

  factory TransactionPaymentDetailModel.fromJson(Map<String, dynamic> json) {
    return TransactionPaymentDetailModel(
      id: json['id'],
      billNumber: json['billNumber'],
      category: json['category'] != null ? HospitalServiceCategoryEnum.values.byName(json['category']) : null,
      services: json['services'] != null
          ? List<HospitalService>.from(json['services'].map((service) => HospitalService.fromJson(service)))
          : null,
    );
  }

  Map<String, dynamic> toJson() => {
        'id': id,
        'billNumber': billNumber,
        'category': category,
        'services': services != null && services!.isNotEmpty ? [...?services?.map((HospitalService e) => e.toJson())] : [],
      };
}

class TransactionPayerDetailModel {
  num? id;
  String? fullName;
  String? phoneNumber;
  String? patientNumber;

  TransactionPayerDetailModel({
    this.id,
    this.fullName,
    this.phoneNumber,
    this.patientNumber,
  });

  factory TransactionPayerDetailModel.fromJson(Map<String, dynamic> json) {
    return TransactionPayerDetailModel(
      id: json['id'],
      fullName: json['fullName'],
      phoneNumber: json['phoneNumber'],
      patientNumber: json['patientNumber'],
    );
  }

  Map toJson() => {
        'id': id,
        'fullName': fullName,
        'phoneNumber': phoneNumber,
        'patientNumber': patientNumber,
      };
}

class TransactionStatusMessage {
  String title;
  String subTitle;
  String header;
  IconData iconData;

  TransactionStatusMessage({
    required this.title,
    required this.subTitle,
    required this.header,
    required this.iconData,
  });
}
