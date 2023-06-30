enum SupportChannelEnum { LIVE_CHAT, TICKET, EMAIL, FAQ }

enum EnvironmentEnum { live, test }

enum AuthStatusEnum { loggedIn, notLoggedIn, loggedOut, registered, notRegistered, authenticating }

enum SuccessOrErrorEnum { success, error }

enum AccountTypeEnum { SUPER_USER, ADMIN_USER, AGENT_USER, REGULAR_USER }

enum BillTypeEnum { DRUG, SERVICE }

enum CollectionModeEnum { MANUAL, GATEWAY }

enum TransactionStatusEnum {
  INITIATED,
  PENDING,
  SUCCESSFUL,
  CANCELLED,
  FAILED,
}

enum HospitalServiceCategoryEnum { DRUG, SERVICE }
