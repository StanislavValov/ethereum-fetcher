package com.limechain.limeapi.integration;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthTransaction;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EthIntegration {
    private final Web3j web3j;

    public List<EthTransaction> findTransactionsBy(List<String> transactionHashes) {
        return transactionHashes.stream()
                .map(current -> {
                    try {
                        return web3j.ethGetTransactionByHash(current).send();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
    }
}
