package com.james.medpay.features.transaction.data.interactor.contract;

import com.james.medpay.core.params.PageResult;
import com.james.medpay.core.params.PageSearchRequest;
import com.james.medpay.features.transaction.data.request.TransactionRecentRequest;
import com.james.medpay.features.transaction.data.request.TransactionRequest;
import com.james.medpay.features.transaction.data.request.searchRequest.TransactionSearchRequest;
import com.james.medpay.features.transaction.domain.entity.enums.TransactionSearchBy;
import com.james.medpay.features.transaction.domain.entity.enums.TransactionStatusEnum;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface TransactionBaseInteractor {

	ResponseEntity<TransactionRequest> createTransaction( TransactionRequest transaction );

	ResponseEntity<List<TransactionRecentRequest>> getRecentTransactions(Long hospitalId, Long userId);

	ResponseEntity<PageResult<TransactionRequest>> getTransactionBySearchRequest( PageSearchRequest<TransactionSearchRequest> requestPage);

	ResponseEntity<Optional<TransactionRequest>> getTransactionByReferenceOrId( TransactionSearchBy searchBy, String value, boolean trim );

	ResponseEntity<Optional<TransactionStatusEnum>> getTransactionStatusByReferenceOrId( TransactionSearchBy searchBy, String value );

	ResponseEntity<Optional<TransactionStatusEnum>> getTransactionStatusByReference( String reference );

	ResponseEntity<Optional<TransactionStatusEnum>> getTransactionStatusById( Long value );

	ResponseEntity<Optional<TransactionRequest>> getByReference( String reference, boolean trim );

	ResponseEntity<Optional<TransactionRequest>> getById( Long transactionId, boolean trim );

	ResponseEntity<byte[]> getTransactionReceiptPdfBytes( String reference, String token );

	ResponseEntity<List<TransactionRequest>> getAll();

	ResponseEntity<Optional<TransactionRequest>> getOneByReferenceAndToken( String reference, String token, boolean trim );

}
