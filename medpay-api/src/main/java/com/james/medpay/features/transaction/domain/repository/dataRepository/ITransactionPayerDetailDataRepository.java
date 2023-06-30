
package com.james.medpay.features.transaction.domain.repository.dataRepository;

import com.james.medpay.features.transaction.domain.entity.TransactionPayerDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransactionPayerDetailDataRepository extends JpaRepository<TransactionPayerDetail, Long> {

}
