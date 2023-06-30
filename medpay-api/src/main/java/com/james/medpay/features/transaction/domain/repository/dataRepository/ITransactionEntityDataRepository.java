
package com.james.medpay.features.transaction.domain.repository.dataRepository;

import com.james.medpay.features.hospital.domain.entity.HospitalEntity;
import com.james.medpay.features.transaction.domain.entity.TransactionEntity;
import com.james.medpay.features.users.domain.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ITransactionEntityDataRepository extends JpaRepository<TransactionEntity, Long>, JpaSpecificationExecutor<TransactionEntity> {

	List<TransactionEntity> findAllByHospitalAndUserAndDateIsBetween(
			HospitalEntity hospital, UserEntity user, LocalDate start, LocalDate end
	);

	Page<TransactionEntity> findAll( Specification<TransactionEntity> specification, Pageable pageable );

	List<TransactionEntity> findAll(Specification<TransactionEntity> specification);

	Optional<TransactionEntity> findByReferenceEquals( String reference );

	Optional<TransactionEntity> findByReferenceEqualsAndTokenEquals( String reference, String token );

	List<TransactionEntity> findAllByReferenceContainsIgnoreCase( String reference );

	List<TransactionEntity> findAllByUserAndHospitalAndDateIsLessThanEqualAndDateGreaterThanEqual(
			@NotNull UserEntity user,
			@NotNull HospitalEntity hospital,
			LocalDate endDate,
			LocalDate startDate
	);

	List<TransactionEntity> findAllByHospitalAndDateIsLessThanEqualAndDateIsGreaterThanEqualAndAmountIsLessThanEqualAndAmountIsGreaterThanEqual(
			@NotNull HospitalEntity hospital,
			LocalDate endDate,
			LocalDate startDate,
			BigDecimal maxAmount,
			BigDecimal minAmount
	);

	List<TransactionEntity> findAllByDateIsLessThanEqualAndDateGreaterThanEqual(
			LocalDate endDate, LocalDate startDate
	);

	List<TransactionEntity> findAllByHospitalAndDateIsLessThanEqualAndDateGreaterThanEqual(
			@NotNull HospitalEntity hospital, LocalDate date, LocalDate date2
	);
}
