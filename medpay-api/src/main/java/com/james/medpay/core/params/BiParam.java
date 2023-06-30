package com.james.medpay.core.params;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BiParam<L, R> {
	L left;
	R right;
}
