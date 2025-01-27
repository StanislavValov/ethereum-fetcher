package com.limechain.etherium.http.controller;

import com.limechain.etherium.domain.entity.EthTransactionEntity;
import com.limechain.etherium.domain.service.AuthService;
import com.limechain.etherium.domain.service.EthService;
import com.limechain.etherium.domain.service.UserService;
import com.limechain.etherium.http.request.LoginRequestDTO;
import com.limechain.etherium.http.response.JwtResponseDTO;
import com.limechain.etherium.http.response.TransactionsDTO;
import com.limechain.etherium.utils.JwtUtil;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/lime")
@AllArgsConstructor
public class LimeController {
    public static final String TRANSACTION_HASHES = "transactionHashes";

    private EthService ethService;
    private AuthService authService;
    private UserService userService;

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
    public ResponseEntity<?> authenticate(@RequestBody LoginRequestDTO loginRequestDTO) {
        String token = authService.authenticate(loginRequestDTO.username(), loginRequestDTO.password());

        return ResponseEntity.ok(new JwtResponseDTO(token));
    }

    @GetMapping("/my")
    public ResponseEntity<?> getUserTransactions() {
        try {
            return null;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
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