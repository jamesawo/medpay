
package com.james.medpay.features.transaction.domain.repository.dataRepository;

import com.james.medpay.features.transaction.domain.entity.TransactionPaymentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransactionPaymentDetailDataRepository extends JpaRepository<TransactionPaymentDetail, Long> {
}
