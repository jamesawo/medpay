package com.james.medpay.core.params;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Param<T> {
	Long id;
	T model;
}
