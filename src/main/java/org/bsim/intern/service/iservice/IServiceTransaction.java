package org.bsim.intern.service.iservice;

import org.bsim.intern.shared.dto.TransactionDTO;
import org.bsim.intern.shared.dto.WalletDTO;

import java.util.List;

public interface IServiceTransaction {

    List<TransactionDTO> getAllTransaction();
    TransactionDTO addNewTransaction(String walletId, TransactionDTO transactionDTO);
    List<TransactionDTO> getAllTransactionByWalletId(String walletId);
    TransactionDTO updateTransactionByTransactionId(String walletId, String TransactionId, TransactionDTO transactionDTO);
    TransactionDTO deleteTransaction(String walletId, String TransactionId);
}
