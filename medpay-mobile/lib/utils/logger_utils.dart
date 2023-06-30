import 'dart:developer';

/// LoggerUtils
/// Use this class to implement all log type.
/// Makes it easy to change the implementation of any logger service
/// Decided upon to use in this application at any time.
///
class LoggerUtils {
  static logError({required dynamic error, required String caller}) {
    log(error.toString(), name: caller);
  }
}
