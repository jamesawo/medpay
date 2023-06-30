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
        items: List<BillItem>.from(json['items'].map((item) => BillItem.fromJson(item))),
        cashier: BillCashier.fromJson(json['cashier']),
      );
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
