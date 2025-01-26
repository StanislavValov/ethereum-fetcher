package com.limechain.etherium.integration;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.core.methods.response.ShhMessages;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

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
