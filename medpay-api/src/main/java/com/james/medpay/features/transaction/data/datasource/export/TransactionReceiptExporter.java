package com.james.medpay.features.transaction.data.datasource.export;

import com.james.medpay.core.contract.DataExporter;
import com.james.medpay.core.util.ExportUtil;
import com.james.medpay.core.util.HelperUtil;
import com.james.medpay.features.hospital.domain.entity.HospitalDetail;
import com.james.medpay.features.transaction.domain.entity.TransactionEntity;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JREmptyDataSource;
import org.springframework.stereotype.Component;

import java.util.HashMap;

import static com.james.medpay.features.transaction.data.datasource.enums.TransactionFilesEnum.TRANSACTION_RECEIPT;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Component
@RequiredArgsConstructor
public class TransactionReceiptExporter implements DataExporter<byte[], TransactionEntity> {

	public static final String EMPTY = "";

	@Override
	public byte[] export( TransactionEntity data ) {
		HashMap<String, Object> map = _getReceiptDataMap( data );
		return ExportUtil.generatePDFBytes( map, TRANSACTION_RECEIPT.asInputStream(), new JREmptyDataSource() );
	}

	private HashMap<String, Object> _getReceiptDataMap( TransactionEntity data ) {
		HashMap<String, Object> map = new HashMap<>();
		HospitalDetail detail = data.getHospital().getDetail();
		map.put( "hospitalName", isNotEmpty( detail ) ? detail.getName() : EMPTY );
		map.put( "hospitalAddress", isNotEmpty( detail ) ? detail.getHospitalAddress() : EMPTY );
		map.put( "hospitalLogo", isNotEmpty( detail.getHospitalLogoUrl() ) ? detail.getHospitalLogoUrl() : EMPTY );
		map.put( "supportStaff", data.getUser().getBasicDetails().getFullName() );
		map.put( "receiptTime", HelperUtil.formatTime( data.getTime() ) );
		map.put( "receiptDate", HelperUtil.formatDate( data.getDate() ) );
		map.put( "payerPhone", data.getPayerDetail().getPhoneNumber() );
		map.put( "payerName", data.getPayerDetail().getFullName() );
		map.put( "receiptRef", data.getReference() );
		map.put( "receiptSerial", data.getToken() );
		map.put( "amountPaid", HelperUtil.formatAmount( data.getAmount().doubleValue() ) );
		return map;
	}

}
