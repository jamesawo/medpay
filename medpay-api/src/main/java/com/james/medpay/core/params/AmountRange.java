package com.james.medpay.core.params;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AmountRange {
	BigDecimal minAmount;
	BigDecimal maxAmount;
}
