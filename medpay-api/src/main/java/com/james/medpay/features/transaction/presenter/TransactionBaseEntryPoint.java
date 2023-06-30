
package com.james.medpay.features.transaction.presenter;


import com.james.medpay.core.params.PageResult;
import com.james.medpay.core.params.PageSearchRequest;
import com.james.medpay.features.transaction.data.interactor.implementation.TransactionBaseInteractorImpl;
import com.james.medpay.features.transaction.data.request.TransactionRecentRequest;
import com.james.medpay.features.transaction.data.request.TransactionRequest;
import com.james.medpay.features.transaction.data.request.searchRequest.TransactionSearchRequest;
import com.james.medpay.features.transaction.domain.entity.enums.TransactionSearchBy;
import com.james.medpay.features.transaction.domain.entity.enums.TransactionStatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static com.james.medpay.core.constant.Routes.API_PREFIX;
import static com.james.medpay.core.constant.Routes.TRANSACTION_ENDPOINT;

@RestController
@RequestMapping( value = API_PREFIX + TRANSACTION_ENDPOINT )
@RequiredArgsConstructor
public class TransactionBaseEntryPoint {

	private final TransactionBaseInteractorImpl interactor;

	@PostMapping( value = "/create" )
	public ResponseEntity<TransactionRequest> createTransaction(
			@Valid @RequestBody TransactionRequest request
	) {
		return this.interactor.createTransaction( request );
	}

	@GetMapping(value = "/recent-by-hospital-and-user")
	public ResponseEntity<List<TransactionRecentRequest>> getRecentTransactions(
			@RequestParam(value = "user") Long userId,
			@RequestParam(value = "hospital") Long hospitalId
	){
		return this.interactor.getRecentTransactions( hospitalId, userId );
	}

	@PostMapping(value = "/by-search-request")
	public ResponseEntity<PageResult<TransactionRequest>> getBySearchRequest( @RequestBody PageSearchRequest<TransactionSearchRequest> request ){
		return this.interactor.getTransactionBySearchRequest( request );
	}

	@GetMapping( value = "/id-or-ref/{value}" )
	public ResponseEntity<Optional<TransactionRequest>> getTransactionByIdOrRef(
			@PathVariable( value = "value" ) String value,
			@RequestParam( value = "searchBy", required = false, defaultValue = "TRN_ID" ) TransactionSearchBy searchBy,
			@RequestParam( value = "trim", required = false, defaultValue = "true" ) boolean trim
	) {
		return this.interactor.getTransactionByReferenceOrId( searchBy, value, trim );
	}

	@GetMapping( value = "/by-reference-and-token" )
	public ResponseEntity<Optional<TransactionRequest>> getTransactionByReferenceAndToken(
			@RequestParam( value = "reference" ) String reference,
			@RequestParam( value = "token" ) String token,
			@RequestParam( value = "trim", required = false, defaultValue = "true" ) boolean trim
	) {
		return this.interactor.getOneByReferenceAndToken( reference, token, trim );
	}

	@GetMapping( value = "/status/{value}" )
	public ResponseEntity<Optional<TransactionStatusEnum>> getTransactionStatusByRefOrId(
			@RequestParam( value = "searchBy", required = false, defaultValue = "TRN_ID" ) TransactionSearchBy searchBy,
			@PathVariable( value = "value" ) String value
	) {
		return this.interactor.getTransactionStatusByReferenceOrId( searchBy, value );
	}

	@GetMapping("/download-pdf-receipt")
	public ResponseEntity<byte[]> downloadReceiptPdf(
			@RequestParam( value = "reference" ) String reference,
			@RequestParam( value = "token" ) String token
	) {
		return this.interactor.getTransactionReceiptPdfBytes( reference, token );
	}
}
