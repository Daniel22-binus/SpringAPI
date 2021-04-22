package org.bsim.intern.service.impl;

import org.bsim.intern.io.entity.TransactionEntity;
import org.bsim.intern.io.entity.WalletEntity;
import org.bsim.intern.io.irepository.TransactionRepository;
import org.bsim.intern.io.irepository.WalletsRepository;
import org.bsim.intern.service.iservice.IServiceTransaction;
import org.bsim.intern.shared.dto.TransactionDTO;
import org.bsim.intern.shared.utils.GenerateRandomPublicId;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionImpl implements IServiceTransaction {

    private final TransactionRepository transactionRepository;
    private final WalletsRepository walletsRepository;
    private final GenerateRandomPublicId generateRandomPublicId;

    public TransactionImpl(TransactionRepository transactionRepository, WalletsRepository walletsRepository, GenerateRandomPublicId generateRandomPublicId) {
        this.transactionRepository = transactionRepository;
        this.walletsRepository = walletsRepository;
        this.generateRandomPublicId = generateRandomPublicId;
    }

    @Override
    public List<TransactionDTO> getAllTransaction() {

        ModelMapper mapper = new ModelMapper();

        List <TransactionEntity> entity = transactionRepository.findAll();
        List<TransactionDTO> returnValue = new ArrayList<>();

        for (TransactionEntity transactionEntity : entity)
        {
            returnValue.add(mapper.map(transactionEntity, TransactionDTO.class));
        }

        return returnValue;
    }

    @Override
    public TransactionDTO addNewTransaction(String walletId, TransactionDTO transactionDTO) {

        ModelMapper mapper = new ModelMapper();

        WalletEntity walletEntity = walletsRepository.findByWalletid(walletId);

        TransactionEntity entity = mapper.map(transactionDTO, TransactionEntity.class);
        entity.setWalletEntity(walletEntity);
        entity.setTransactionId(generateRandomPublicId.generateUserId(35));

        TransactionEntity storedValue = transactionRepository.save(entity);

        return mapper.map(storedValue, TransactionDTO.class);
    }

    @Override
    public List<TransactionDTO> getAllTransactionByWalletId(String walletId) {

        WalletEntity walletEntity = walletsRepository.findByWalletid(walletId);
        ModelMapper mapper = new ModelMapper();
        List<TransactionEntity> transactionEntities = transactionRepository.findAllByWalletEntity(walletEntity);
        List<TransactionDTO> returnValue = new ArrayList<>();

        for (TransactionEntity entity : transactionEntities)
        {
            returnValue.add(mapper.map(entity, TransactionDTO.class));
        }

        return returnValue;
    }

    @Override
    public TransactionDTO updateTransactionByTransactionId(String walletId, String TransactionId, TransactionDTO transactionDTO) {

        long balance = 0;
        long prevAmount = 0;
        long newAmount = 0;

        WalletEntity walletEntity = walletsRepository.findByWalletid(walletId);
        TransactionEntity entity = transactionRepository.findByTransactionId(TransactionId);

        balance = walletEntity.getBalance();
        prevAmount = entity.getAmount();
        newAmount = transactionDTO.getAmount();

        balance = balance + prevAmount - newAmount;


        walletEntity.setBalance(balance);
        walletsRepository.save(walletEntity);
        entity.setWalletEntity(walletEntity);
        entity.setAmount(newAmount);


        TransactionEntity updatedEntity = transactionRepository.save(entity);
        return new ModelMapper().map(updatedEntity, TransactionDTO.class);
    }

    @Override
    public TransactionDTO deleteTransaction(String walletId, String TransactionId) {

        WalletEntity walletEntity = walletsRepository.findByWalletid(walletId);
        TransactionEntity entity = transactionRepository.findByTransactionId(TransactionId);

        entity.setWalletEntity(walletEntity);
        entity.setDeleted(true);

        TransactionEntity storedData =  transactionRepository.save(entity);

        return new ModelMapper().map(storedData, TransactionDTO.class);
    }
}
