package com.james.medpay.features.transaction.data.datasource.enums;

import com.james.medpay.core.util.ExportUtil;

import java.io.InputStream;

public enum TransactionFilesEnum {

	TRANSACTION_RECEIPT {
		final String name = "/payment_receipt";

		@Override
		public InputStream asInputStream() {
			//String path = new ClassPathResource( SRC + name + EXT ).getPath();
			return ExportUtil.getFileFromClassPathAsInputStream( SRC + name + EXT );
		}
	},

	TRANSACTION_DAILY_COLLECTION_REPORT {
		final String name = "/hospital_collection_report";

		@Override
		public InputStream asInputStream() {
			return ExportUtil.getFileFromClassPathAsInputStream( SRC + name + EXT );
		}
	},

	TRANSACTION_AGENT_COLLECTION_REPORT {
		final String name = "/agent_collection_report";

		@Override
		public InputStream asInputStream() {
			return ExportUtil.getFileFromClassPathAsInputStream( SRC + name + EXT );
		}
	};


	public abstract InputStream asInputStream();

	public static final String SRC = "/transaction";
	public static final String EXT = ".jrxml";
}
