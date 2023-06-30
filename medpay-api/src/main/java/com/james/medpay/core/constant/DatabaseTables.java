package com.james.medpay.core.constant;

public class DatabaseTables {

	public static final String DEFAULT_SUPER_ADMIN_ROLE = "SuperAdmin";

	private static final String TABLE_PREFIX = "paymed_";
	private static final String TABLE_SUFFIX = "_records";

	// user module
	public static final String USER_TABLE = TABLE_PREFIX + "user" + TABLE_SUFFIX;
	public static final String USER_BASIC_DETAILS_TABLE = TABLE_PREFIX + "user_detail" + TABLE_SUFFIX;
	public static final String USER_CONFIGURATION_TABLE = TABLE_PREFIX + "user_config" + TABLE_SUFFIX;
	public static final String USER_ADDRESS_TABLE = TABLE_PREFIX + "user_address" + TABLE_SUFFIX;

	// hospital module
	public static final String HOSPITAL_TABLE = TABLE_PREFIX + "hospital" + TABLE_SUFFIX;
	public static final String HOSPITAL_DETAIL_TABLE = TABLE_PREFIX + "hospital_detail" + TABLE_SUFFIX;
	public static final String HOSPITAL_API_CONFIG_TABLE = TABLE_PREFIX + "hospital_api_config" + TABLE_SUFFIX;
	public static final String HOSPITAL_SETTLEMENT_TABLE = TABLE_PREFIX + "hospital_settlement_config" + TABLE_SUFFIX;
	public static final String HOSPITAL_SERVICE_GROUP_TABLE = TABLE_PREFIX + "hospital_service_group" + TABLE_SUFFIX;
	public static final String HOSPITAL_REVENUE_HEAD_TABLE = TABLE_PREFIX + "hospital_revenue_head" + TABLE_SUFFIX;
	public static final String HOSPITAL_SERVICE_TABLE = TABLE_PREFIX + "hospital_service" + TABLE_SUFFIX;

	// transaction module
	public static final String TRANSACTION_TABLE = TABLE_PREFIX + "transaction" + TABLE_SUFFIX;
	public static final String TRANSACTION_PAYER_TABLE = TABLE_PREFIX + "transaction_payer" + TABLE_SUFFIX;
	public static final String TRANSACTION_PAYMENT_TABLE = TABLE_PREFIX + "transaction_payment" + TABLE_SUFFIX;
	public static final String TRANSACTION_SETTLEMENT_TABLE = TABLE_PREFIX + "transaction_settlement" + TABLE_SUFFIX;

	public static final String TRANSACTION_SERVICE = TABLE_PREFIX + "transaction_service" + TABLE_SUFFIX;



	// Authentication Module
	public static final String ROLE_TABLE = TABLE_PREFIX + "role" + TABLE_SUFFIX;
	public static final String PERMISSION_TABLE = TABLE_PREFIX + "permission" + TABLE_SUFFIX;
	public static final String ROLE_PERMISSION_TABLE = TABLE_PREFIX + "role_permission" + TABLE_SUFFIX;
	public static final String USER_ROLE_TABLE = TABLE_PREFIX + "user_role" + TABLE_SUFFIX;


}
