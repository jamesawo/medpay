
package com.james.medpay.features.transaction.presenter;

import com.james.medpay.features.transaction.data.interactor.implementation.TransactionPayerInteractorImpl;
import com.james.medpay.features.transaction.data.request.TransactionPayerDetailRequest;
import com.james.medpay.features.transaction.domain.entity.enums.TransactionSearchBy;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.james.medpay.core.constant.Routes.API_PREFIX;

@RestController
@RequestMapping( value = API_PREFIX + "/transaction-payer" )
@RequiredArgsConstructor
public class TransactionPayerDetailEntryPoint {
	private final TransactionPayerInteractorImpl interactor;

	@GetMapping( value = "/{value}" )
	public ResponseEntity<Optional<TransactionPayerDetailRequest>> getPayerDetailsForATransactionByReferenceOrId(
			@RequestParam( value = "searchBy", required = false, defaultValue = "TRN_ID" )
					TransactionSearchBy searchBy,
			@PathVariable( value = "value" ) String value
	) {
		return this.interactor.getPayerDetailsByTransactionReferenceOrId( searchBy, value );
	}

}
