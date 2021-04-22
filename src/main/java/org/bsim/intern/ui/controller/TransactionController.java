package org.bsim.intern.ui.controller;

import org.bsim.intern.service.iservice.IServiceTransaction;
import org.bsim.intern.shared.dto.TransactionDTO;
import org.bsim.intern.ui.model.request.TransacrionRequest;
import org.bsim.intern.ui.model.response.TransactionResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {


    private final IServiceTransaction iServiceTransaction;


    public TransactionController(IServiceTransaction iServiceTransaction) {
        this.iServiceTransaction = iServiceTransaction;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<TransactionResponse> getAllTransaction() {
        ModelMapper mapper = new ModelMapper();
        List<TransactionResponse> returnValue = new ArrayList<>();

        List<TransactionDTO> transactionDTOS = iServiceTransaction.getAllTransaction();

        for (TransactionDTO dto : transactionDTOS) {
            returnValue.add(mapper.map(dto, TransactionResponse.class));
        }

        return returnValue;
    }

    @PostMapping(path = "/{walletid}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public TransactionResponse addNewTransaction(@PathVariable String walletid, //nama @path variable harus persis (Case sensitive) dengan di pathnya
                                                 @RequestBody TransacrionRequest transacrionRequest) {
        ModelMapper mapper = new ModelMapper();
        TransactionDTO transactionDTO = mapper.map(transacrionRequest, TransactionDTO.class);
        TransactionDTO storedValue = iServiceTransaction.addNewTransaction(walletid, transactionDTO);
        TransactionResponse returnValue = mapper.map(storedValue, TransactionResponse.class);

        return returnValue;
    }

    @GetMapping(path = "/{walletId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<TransactionResponse> getAllTransactionsByWalletId(@PathVariable String walletId) {
        ModelMapper mapper = new ModelMapper();

        List<TransactionDTO> allTransaction = iServiceTransaction.getAllTransactionByWalletId(walletId);
        List<TransactionResponse> returnValue = new ArrayList<>();

        for (TransactionDTO dto : allTransaction) {
            returnValue.add(mapper.map(dto, TransactionResponse.class));
        }

        return returnValue;
    }

    @PutMapping(path = "/{walletId}/{transactionId}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public TransactionResponse updateTransactions(@PathVariable String walletId,
                                                  @PathVariable String transactionId,
                                                  @RequestBody TransacrionRequest transacrionRequest) {
        ModelMapper mapper = new ModelMapper();

        TransactionDTO transactionDTO = mapper.map(transacrionRequest, TransactionDTO.class);
        TransactionDTO updatedData = iServiceTransaction.updateTransactionByTransactionId(
                walletId, transactionId, transactionDTO
        );

        return mapper.map(updatedData, TransactionResponse.class);

    }

    @DeleteMapping(path = "/{walletId}/{transactionId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public TransactionResponse deleteTransaction(@PathVariable String walletId, @PathVariable String transactionId)
    {
        TransactionDTO deleteData = iServiceTransaction.deleteTransaction(walletId, transactionId);
        return new ModelMapper().map(deleteData, TransactionResponse.class);
    }

}
