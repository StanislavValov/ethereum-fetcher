package com.limechain.limeapi.http.controller;

import com.limechain.limeapi.domain.entity.EthTransactionEntity;
import com.limechain.limeapi.domain.entity.UserSearchHistoryEntity;
import com.limechain.limeapi.domain.service.AuthService;
import com.limechain.limeapi.domain.service.EthService;
import com.limechain.limeapi.domain.service.UserHistoryService;
import com.limechain.limeapi.http.request.LoginRequestDTO;
import com.limechain.limeapi.http.response.JwtResponseDTO;
import com.limechain.limeapi.http.response.TransactionsDTO;
import com.limechain.limeapi.http.response.UserSearchHistoryDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.limechain.limeapi.utils.RlpUtil.mapToRlpHex;

@RestController
@RequestMapping("/lime")
@AllArgsConstructor
public class LimeController {
    private static final String TRANSACTION_HASHES = "transactionHashes";
    private static final String USERNAME = "username";

    private EthService ethService;
    private AuthService authService;
    private UserHistoryService userHistoryService;

    @GetMapping("/eth")
    public ResponseEntity<TransactionsDTO> getTransactionsBy(@RequestParam(name = TRANSACTION_HASHES) List<String> transactionHashes) {
        List<EthTransactionEntity> ethTransactions = ethService.findTransactionsByHash(transactionHashes);

        TransactionsDTO response = this.toTransactionsDTO(ethTransactions);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/eth/all")
    public ResponseEntity<TransactionsDTO> getAllTransactions() {
        List<EthTransactionEntity> ethTransactions = ethService.findAllTransactions();

        TransactionsDTO response = this.toTransactionsDTO(ethTransactions);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody LoginRequestDTO loginRequestDTO) {
        String token = authService.authenticate(loginRequestDTO.username(), loginRequestDTO.password());

        return ResponseEntity.ok(new JwtResponseDTO(token));
    }

    @GetMapping("/my")
    public ResponseEntity<?> getUserTransactions(HttpServletRequest request) {
        try {
            String username = (String) request.getAttribute(USERNAME);

            List<UserSearchHistoryEntity> userSearchHistory = userHistoryService.getUserSearchesAsRlp(username);

            List<UserSearchHistoryDTO> dto = toHistoryDTO(userSearchHistory);

            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    private TransactionsDTO toTransactionsDTO(List<EthTransactionEntity> ethTransactions) {
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

    private List<UserSearchHistoryDTO> toHistoryDTO(List<UserSearchHistoryEntity> entities) {
        return entities.stream()
                .map(entity -> {
                    String aggregatedTransactionHashes = mapToRlpHex(entity.getTransactionHashSet());
                    return new UserSearchHistoryDTO(
                            entity.getId(),
                            entity.getUsername(),
                            aggregatedTransactionHashes,
                            entity.getSearchedAt()
                    );
                })
                .collect(Collectors.toList());
    }
}