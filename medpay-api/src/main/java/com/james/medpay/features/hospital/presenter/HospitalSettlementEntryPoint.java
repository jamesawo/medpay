
package com.james.medpay.features.hospital.presenter;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.james.medpay.core.constant.Routes.API_PREFIX;


@RestController
@RequestMapping( value = API_PREFIX + "/hospital-settlement" )
@RequiredArgsConstructor
public class HospitalSettlementEntryPoint {

}
