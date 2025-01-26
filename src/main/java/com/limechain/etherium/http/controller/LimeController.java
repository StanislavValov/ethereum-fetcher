package com.limechain.etherium.http.controller;

import com.limechain.etherium.domain.entity.EthTransactionEntity;
import com.limechain.etherium.domain.service.EthService;
import com.limechain.etherium.http.request.AuthRequestDTO;
import com.limechain.etherium.http.response.GwtTokenDTO;
import com.limechain.etherium.http.response.TransactionsDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lime")
@AllArgsConstructor
public class LimeController {
    public static final String TRANSACTION_HASHES = "transactionHashes";
    private EthService ethService;

    @GetMapping("/eth")
    public ResponseEntity<TransactionsDTO> getTransactionsBy(@RequestParam(name = TRANSACTION_HASHES) List<String> transactionHashes) {
        List<EthTransactionEntity> ethTransactions = ethService.findTransactionsByHash(transactionHashes);

        TransactionsDTO response = adapt(ethTransactions);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/eth/all")
    public ResponseEntity<TransactionsDTO> getAllTransactions() {
        List<EthTransactionEntity> ethTransactions = ethService.findAllTransactions();

        TransactionsDTO response = adapt(ethTransactions);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<GwtTokenDTO> authenticate(@RequestBody AuthRequestDTO authRequestDTO) {

        return ResponseEntity.ok().body(null);
    }

    private TransactionsDTO adapt(List<EthTransactionEntity> ethTransactions) {
        TransactionsDTO response = new TransactionsDTO();

        List<TransactionsDTO.Transaction> list = ethTransactions.stream()
                .map(entity -> TransactionsDTO.Transaction.builder()
                        .to(entity.getToAddress())
                        .input(entity.getInput())
                        .from(entity.getFromAddress())
                        .value(entity.getValue())
                        .contractAddress(entity.getContractAddress())
                        .blockHash(entity.getBlockHash())
                        .blockNumber(entity.getBlockNumber())
                        .logsCount(entity.getLogsCount())
                        .transactionStatus(entity.getTransactionStatus())
                        .transactionHash(entity.getTransactionHash())
                        .build()).toList();
        response.setTransactions(list);

        return response;
    }
}