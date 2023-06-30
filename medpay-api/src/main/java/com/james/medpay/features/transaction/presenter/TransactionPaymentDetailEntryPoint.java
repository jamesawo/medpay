
package com.james.medpay.features.transaction.presenter;


import com.james.medpay.features.transaction.data.interactor.implementation.TransactionPaymentInteractorImpl;
import com.james.medpay.features.transaction.data.request.TransactionPaymentDetailRequest;
import com.james.medpay.features.transaction.domain.entity.enums.TransactionSearchBy;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.james.medpay.core.constant.Routes.API_PREFIX;

@RestController
@RequestMapping( value = API_PREFIX + "/transaction-payment" )
@RequiredArgsConstructor
public class TransactionPaymentDetailEntryPoint {

	private final TransactionPaymentInteractorImpl interactor;

	@GetMapping( value = "/{value}" )
	public ResponseEntity<Optional<TransactionPaymentDetailRequest>> getPayerDetailsForATransactionByReferenceOrId(
			@RequestParam( value = "searchBy", required = false, defaultValue = "TRN_ID" ) TransactionSearchBy searchBy,
			@PathVariable( value = "value" ) String value
	) {
		return this.interactor.getPayerDetailsByTransactionReferenceOrId( searchBy, value );
	}

}
