package com.james.medpay.core.params;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DateRange {
	LocalDate startDate;
	LocalDate endDate;
}
