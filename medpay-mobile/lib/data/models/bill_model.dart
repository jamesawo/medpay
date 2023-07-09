import 'package:medpay/data/models/hospital_service.dart';

class HospitalBill {
  String? date;
  String? billNumber;
  BillTotal? total;
  BillPatient? patient;
  List<BillItem>? items;
  BillCashier? cashier;
  bool? hasTotal;
  bool? hasPatient;
  bool? hasItems;
  bool? hasCashier;
  List<HospitalService>? services;
  bool? isPaid;

  HospitalBill({
    this.date,
    this.billNumber,
    this.total,
    this.patient,
    this.items,
    this.cashier,
    this.hasTotal = false,
    this.hasPatient = false,
    this.hasItems = false,
    this.hasCashier = false,
  });

  factory HospitalBill.fromJson(Map<String, dynamic>? json) {
    if (json != null) {
      return HospitalBill(
        date: json['date'].toString(),
        billNumber: json['billNumber'].toString(),
        hasTotal: json['hasTotal'],
        hasPatient: json['hasPatient'],
        hasItems: json['hasItems'],
        hasCashier: json['hasCashier'],
        total: BillTotal.fromJson(json['total']),
        patient: BillPatient.fromJson(json['patient']),
        items: List<BillItem>.from(
            json['items'].map((item) => BillItem.fromJson(item))),
        cashier: BillCashier.fromJson(json['cashier']),
      );
    }
    return HospitalBill();
  }

  factory HospitalBill.fromInvoiceJson(dynamic json) {
    if (json != null) {

      var billItems = List<BillItem>.from(
        json['items'].map(
          (item) => BillItem(
              quantity: "",
              description: item["service"]["title"],
              amount: item["amount"].toString()),
        ),
      );

      var servicesItems = List<HospitalService>.from(
        json['items'].map(
          (item) => HospitalService(
            title: item["service"]["title"],
            code: "",
            id: item["service"]["id"],
            isEnabled: true,
          ),
        ),
      );

      var bill = HospitalBill(
        date: json['createdAt'],
        billNumber: json['billNumber'].toString(),
        hasTotal: true,
        hasPatient: true,
        hasItems: true,
        hasCashier: false,
        total: BillTotal(
            netAmount: json['billAmount'].toString(),
            discountAmount: '0',
            grossAmount: '0'),
        patient: BillPatient(
            phoneNumber: json['patient']['phone'],
            fullName: json['patient']['fullName'],
            patientNumber: json['patient']['uniqueCode']),
        items: billItems,
        cashier: BillCashier(
          name: json["createdBy"]["nickName"],
          location: "",
        ),
      );

      bill.services = servicesItems;
      bill.isPaid = json['status'] != null && json['status'] == 'PAID';

      return bill;
    }
    return HospitalBill();
  }
}

class BillTotal {
  String? netAmount;
  String? discountAmount;
  String? grossAmount;

  BillTotal({this.netAmount, this.discountAmount, this.grossAmount});

  factory BillTotal.fromJson(Map<String, dynamic>? json) {
    if (json != null) {
      return BillTotal(
        netAmount: json['netAmount'].toString(),
        discountAmount: json['discountAmount'].toString(),
        grossAmount: json['grossAmount'].toString(),
      );
    }
    return BillTotal();
  }
}

class BillPatient {
  String? phoneNumber;
  String? fullName;
  String? patientNumber;

  BillPatient({this.phoneNumber, this.fullName, this.patientNumber});

  factory BillPatient.fromJson(Map<String, dynamic>? json) {
    if (json != null) {
      return BillPatient(
        phoneNumber: json['phoneNumber'].toString(),
        fullName: json['fullName'].toString(),
        patientNumber: json['patientNumber'].toString(),
      );
    }
    return BillPatient();
  }
}

class BillItem {
  String? quantity;
  String? description;
  String? amount;

  BillItem({this.quantity, this.description, this.amount});

  factory BillItem.fromJson(Map<String, dynamic>? json) {
    if (json != null) {
      return BillItem(
        quantity: json['quantity'].toString(),
        description: json['description'].toString(),
        amount: json['amount'].toString(),
      );
    }
    return BillItem();
  }
}

class BillCashier {
  String? name;
  String? location;

  BillCashier({this.name, this.location});

  factory BillCashier.fromJson(Map<String, dynamic>? json) {
    if (json != null) {
      return BillCashier(
        name: json['name'].toString(),
        location: json['location'].toString(),
      );
    }
    return BillCashier();
  }
}
