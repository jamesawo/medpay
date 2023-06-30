import 'package:flutter/material.dart';
import 'package:medpay/utils/app_dimensions.dart';
import 'package:medpay/utils/style_utils.dart';
import 'package:medpay/widgets/text_small.dart';

class ContinueWithGoogle extends StatefulWidget {
  const ContinueWithGoogle({Key? key}) : super(key: key);

  @override
  State<ContinueWithGoogle> createState() => _ContinueWithGoogleState();
}

class _ContinueWithGoogleState extends State<ContinueWithGoogle> {
  @override
  Widget build(BuildContext context) {
    return InkWell(
      onTap: () {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text('Please Wait...'),
          ),
        );
      },
      child: Container(
        height: AppDimensions.height40,
        width: AppDimensions.width273,
        padding: EdgeInsets.only(left: AppDimensions.width20, right: AppDimensions.width10),
        decoration: BoxDecoration(
          border: Border.all(color: ThemeColorStyle.appGray50, width: 1),
          borderRadius: BorderRadius.circular(AppDimensions.radius12),
        ),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Image(image: const AssetImage("assets/images/google.png"), height: AppDimensions.height35),
            SizedBox(width: AppDimensions.width10),
            TextSmall(
              text: 'Continue with Google',
              size: AppDimensions.fontSize14,
            ),
          ],
        ),
      ),
    );
  }
}
