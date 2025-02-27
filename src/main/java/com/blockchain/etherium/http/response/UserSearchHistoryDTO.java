package com.blockchain.etherium.http.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Data
public class UserSearchHistoryDTO {
    private UUID id;
    private String username;
    private String transactionHash;
    private LocalDateTime searchedAt;
}