import 'package:flutter/material.dart';
import 'package:medpay/data/models/drop_down_list_model.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/style_utils.dart';
import 'package:medpay/widgets/app_top_title.dart';
import 'package:medpay/widgets/text_small.dart';

class SupportSendFeedBackScreen extends StatefulWidget {
  const SupportSendFeedBackScreen({Key? key}) : super(key: key);

  @override
  State<SupportSendFeedBackScreen> createState() => _SupportSendFeedBackScreenState();
}

class _SupportSendFeedBackScreenState extends State<SupportSendFeedBackScreen> {
  DropListModel dropListModel = DropListModel([OptionItem(id: "1", title: "Option 1"), OptionItem(id: "2", title: "Option 2")]);
  OptionItem optionItemSelected = OptionItem(id: '1', title: "Chọn quyền truy cập");

  String? selectedValue;
  // final _dropdownFormKey = GlobalKey<FormState>();

  @override
  void initState() {
    super.initState();
    selectedValue = dropdownItems[0].value;
  }

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
              titleSpacing: 0,
              title: AppTopTitle(title: 'Send Feedback', prefixIcon: Icons.close),
              pinned: true,
            ),
            SliverToBoxAdapter(
              child: Container(
                margin: EdgeInsets.only(left: AppDimensions.width25, right: AppDimensions.width25, top: AppDimensions.height30),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Container(
                      width: double.infinity,
                      height: 200,
                      decoration: const BoxDecoration(
                        image: DecorationImage(
                          image: AssetImage('assets/images/feedback.png'),
                          fit: BoxFit.contain,
                        ),
                      ),
                    ),
                    SizedBox(height: AppDimensions.height35),

                    // type dropdown
                    TextSmall(
                      text: 'Select Type',
                      size: AppDimensions.fontSize14,
                    ),
                    SizedBox(height: AppDimensions.height10),
                    DropdownButtonFormField(
                      decoration: ThemeInputStyle.dropDownFieldStyle,
                      validator: (value) => value == null ? "Select a value" : null,
                      dropdownColor: ThemeColorStyle.appWhite,
                      value: selectedValue,
                      items: dropdownItems,
                      onChanged: (String? value) {
                        print(value);
                      },
                    ),
                    SizedBox(height: AppDimensions.height20),
                    // message
                    TextSmall(
                      text: 'We love to here your thought',
                      size: AppDimensions.fontSize14,
                    ),
                    SizedBox(height: AppDimensions.height10),
                    TextFormField(
                      minLines: 4,
                      maxLines: 5,
                      keyboardType: TextInputType.multiline,
                      decoration: InputDecoration(
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.all(
                            Radius.circular(AppDimensions.radius7),
                          ),
                        ),
                      ),
                    ),

                    SizedBox(height: AppDimensions.height20),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        ElevatedButton(
                          style: ThemeInputStyle.primaryButtonStyle,
                          child: TextSmall(
                            text: 'Send Feedback',
                            size: AppDimensions.fontSize16,
                            color: ThemeColorStyle.appWhite,
                            weight: FontWeight.w600,
                          ),
                          onPressed: () {},
                        ),
                      ],
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

  List<DropdownMenuItem<String>> get dropdownItems {
    List<DropdownMenuItem<String>> menuItems = const [
      DropdownMenuItem(child: Text("Suggestion"), value: "Suggestion"),
      DropdownMenuItem(child: Text("Feedback"), value: "Feedback"),
      DropdownMenuItem(child: Text("Bug Report"), value: "Bug Report"),
      DropdownMenuItem(child: Text("Others"), value: "Others"),
    ];
    return menuItems;
  }
}
