package org.bsim.intern.io.irepository;

import org.bsim.intern.io.entity.TransactionEntity;
import org.bsim.intern.io.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    List<TransactionEntity> findAllByWalletEntity(WalletEntity walletEntity);
    TransactionEntity findByTransactionId(String transactionId);

}
