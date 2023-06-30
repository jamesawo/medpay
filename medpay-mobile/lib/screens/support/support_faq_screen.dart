import 'package:flutter/material.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/style_utils.dart';
import 'package:medpay/widgets/app_faq_tile.dart';
import 'package:medpay/widgets/app_top_title.dart';

class SupportFaqScreen extends StatefulWidget {
  const SupportFaqScreen({Key? key}) : super(key: key);

  @override
  State<SupportFaqScreen> createState() => _SupportFaqScreenState();
}

class _SupportFaqScreenState extends State<SupportFaqScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: ThemeColorStyle.appWhite,
      body: SafeArea(
        child: CustomScrollView(
          slivers: [
            const SliverAppBar(
              automaticallyImplyLeading: false,
              backgroundColor: ThemeColorStyle.appWhite,
              title: AppTopTitle(title: 'FAQ', prefixIcon: Icons.close),
              pinned: true,
            ),
            SliverToBoxAdapter(
              child: Container(
                margin: EdgeInsets.only(left: AppDimensions.width25, right: AppDimensions.width25, top: AppDimensions.height30),
                child: Column(
                  children: [
                    const AppFaqWidgetTile(
                      question: 'Excepteur sint occaecat cupi proident, in culpa qui officia deserunt?',
                      answer:
                          'Duis nisi dolor, ullamcorper vitae turpis non, suscipit tempus erat. Vestibulum scelerisque ante eu facilisis porttitor.',
                    ),
                    SizedBox(height: AppDimensions.height10),
                    const AppFaqWidgetTile(
                      question: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do?',
                      answer: '',
                    ),
                    SizedBox(height: AppDimensions.height10),
                    const AppFaqWidgetTile(
                      question: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do?',
                      answer: '',
                    ),
                    SizedBox(height: AppDimensions.height10),
                    const AppFaqWidgetTile(
                      question: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do?',
                      answer: '',
                    ),
                    SizedBox(height: AppDimensions.height10),
                    const AppFaqWidgetTile(
                      question: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do?',
                      answer: '',
                    ),
                    SizedBox(height: AppDimensions.height10),
                    const AppFaqWidgetTile(
                      question: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do?',
                      answer: '',
                    ),
                    SizedBox(height: AppDimensions.height10),
                    const AppFaqWidgetTile(
                      question: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do?',
                      answer: '',
                    ),
                  ],
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
