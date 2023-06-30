
package com.james.medpay.features.transaction.domain.repository.dataRepository;

import com.james.medpay.features.transaction.domain.entity.TransactionSettlement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransactionSettlementDataRepository extends JpaRepository<TransactionSettlement, Long> {

}
