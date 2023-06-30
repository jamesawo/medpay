import 'package:flutter/material.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/style_utils.dart';
import 'package:medpay/widgets/text_small.dart';

class CustomBottomNavBar extends StatefulWidget {
  const CustomBottomNavBar({Key? key}) : super(key: key);

  @override
  State<CustomBottomNavBar> createState() => _CustomBottomNavBarState();
}

class _CustomBottomNavBarState extends State<CustomBottomNavBar> {
  List<dynamic> navList = [
    {'icon': Icons.person_outline, 'title': 'Account', 'activeIcon': Icons.person},
    {'icon': Icons.home_outlined, 'title': 'Home', 'activeIcon': Icons.home},
    {'icon': Icons.headset_mic_outlined, 'title': 'Support', 'activeIcon': Icons.headset_mic},
  ];
  int currentIndex = 0;
  @override
  Widget build(BuildContext context) {
    return Container(
      height: AppDimensions.height70,
      padding: EdgeInsets.only(
        top: AppDimensions.height10,
        bottom: AppDimensions.height10,
        left: AppDimensions.height30,
        right: AppDimensions.height30,
      ),
      decoration: BoxDecoration(
        boxShadow: [
          BoxShadow(
            color: Colors.grey.withOpacity(0.5),
            spreadRadius: AppDimensions.radius5,
            blurRadius: AppDimensions.radius7,
          ),
          BoxShadow(
            color: Colors.grey.withOpacity(0.5),
            spreadRadius: 1,
            blurRadius: 1,
          ),
        ],
        color: ThemeColorStyle.appBlue,
        borderRadius: BorderRadius.only(
          topLeft: Radius.circular(AppDimensions.radius32),
          topRight: Radius.circular(AppDimensions.radius32),
        ),
      ),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceAround,
        children: List<Widget>.generate(
          navList.length,
          (index) => GestureDetector(
            onTap: () {
              setState(() {
                currentIndex = index;
              });
            },
            child: Container(
              width: AppDimensions.width80,
              color: Colors.transparent,
              padding: EdgeInsets.symmetric(horizontal: AppDimensions.height5),
              // color: Colors.red,
              child: Column(
                children: [
                  Icon(
                    currentIndex == index ? navList[index]['activeIcon'] : navList[index]['icon'],
                    color: currentIndex == index ? ThemeColorStyle.appWhite : ThemeColorStyle.appGray30,
                  ),
                  SizedBox(height: AppDimensions.height2),
                  TextSmall(
                    text: navList[index]['title'],
                    weight: FontWeight.w600,
                    size: AppDimensions.fontSize13,
                    color: currentIndex == index ? ThemeColorStyle.appWhite : ThemeColorStyle.appGray30,
                  ),
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }
}

/*
GestureDetector(
            onTap: () {
              print('Account Page');
              setState(() {
                currentIndex = 0;
              });
            },
            child: Container(
              padding: EdgeInsets.symmetric(horizontal: AppDimensions.width3),
              color: Colors.red,
              child: Column(
                children: [
                  Icon(Icons.person_outline, color: Colors.grey.shade400),
                  SizedBox(height: AppDimensions.height2),
                  TextSmall(
                      text: 'Account', weight: FontWeight.w600, size: AppDimensions.fontSize13, color: Colors.grey.shade400),
                ],
              ),
            ),
          ),
          GestureDetector(
            onTap: () {
              print('Home Page');
              setState(() {
                currentIndex = 1;
              });
            },
            child: Container(
              padding: EdgeInsets.symmetric(horizontal: AppDimensions.width3),
              color: Colors.red,
              child: Column(
                children: [
                  const Icon(Icons.home, color: Colors.white),
                  SizedBox(height: AppDimensions.height2),
                  TextSmall(text: 'Home', weight: FontWeight.w600, size: AppDimensions.fontSize13, color: Colors.white),
                ],
              ),
            ),
          ),
          GestureDetector(
            onTap: () {
              print('Support Page');
              setState(() {
                currentIndex = 2;
              });
            },
            child: Container(
              padding: EdgeInsets.symmetric(horizontal: AppDimensions.width3),
              color: Colors.red,
              child: Column(
                children: [
                  Icon(Icons.headset_outlined, color: Colors.grey.shade400),
                  SizedBox(height: AppDimensions.height2),
                  TextSmall(
                      text: 'Support', weight: FontWeight.w600, size: AppDimensions.fontSize13, color: Colors.grey.shade400),
                ],
              ),
            ),
          ),
 */
