import 'package:get/get.dart';
import 'package:medpay/repositories/user_repository.dart';

class UserController extends GetxController {
  UserRepository userRepository;
  UserController({required this.userRepository});

  List<dynamic> users = [];
}
