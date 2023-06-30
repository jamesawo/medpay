import 'package:flutter/material.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/widgets/app_top_title.dart';
import 'package:medpay/widgets/text_small.dart';

class SettingAboutScreen extends StatelessWidget {
  const SettingAboutScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: Column(
          children: [
            const AppTopTitle(title: 'About Us'),
            Container(
              width: 250,
              height: 300,
              decoration: const BoxDecoration(
                image: DecorationImage(
                  image: AssetImage('assets/images/about.png'),
                  fit: BoxFit.contain,
                ),
              ),
            ),
            Container(
              margin: EdgeInsets.only(
                left: AppDimensions.width25,
                right: AppDimensions.width25,
                bottom: AppDimensions.height15,
              ),
              child: TextSmall(
                text:
                    'Cras vitae suscipit justo. Ut congue turpis in nisl sodales condimentum. Fusce varius aliquam nunc, a consequat sem fermentum sit. ',
                size: AppDimensions.fontSize15,
                overflow: TextOverflow.visible,
              ),
            ),
            Container(
              margin: EdgeInsets.only(
                left: AppDimensions.width25,
                right: AppDimensions.width25,
                bottom: AppDimensions.height15,
              ),
              child: TextSmall(
                text:
                    'Cras vitae suscipit justo. Ut congue turpis in nisl sodales condimentum. Fusce varius aliquam nunc, a consequat sem sit amet.',
                size: AppDimensions.fontSize15,
                overflow: TextOverflow.visible,
              ),
            ),
            Container(
              margin: EdgeInsets.only(
                left: AppDimensions.width25,
                right: AppDimensions.width25,
                bottom: AppDimensions.height10,
              ),
              child: TextSmall(
                text:
                    'Cras vitae suscipit justo. Ut congue turpis in nisl sodales condimentum. Fusce varius aliquam nunc, a consequat sem fermen amet. Duis nisi dolor, ullamcorper vitae turpis non, suscipit tempus erat. Vestibulum scelerisque ante eu facilisis porttitor.',
                size: AppDimensions.fontSize15,
                overflow: TextOverflow.visible,
              ),
            ),
          ],
        ),
      ),
    );
  }
}
