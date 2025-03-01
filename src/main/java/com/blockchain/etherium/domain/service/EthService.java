package com.blockchain.etherium.domain.service;

import com.blockchain.etherium.domain.entity.EthTransactionEntity;
import com.blockchain.etherium.domain.repository.EthRepository;
import com.blockchain.etherium.integration.EthIntegration;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.EthTransaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Service
@AllArgsConstructor
public class EthService {
    private EthIntegration integration;
    private EthRepository repository;

    @Transactional
    public List<EthTransactionEntity> findTransactionsByHash(List<String> transactionHashes) {
        List<EthTransactionEntity> existingEntities = repository.findAllByTransactionHashIn(transactionHashes);

        Set<String> existingHashes = existingEntities.stream()
                .map(EthTransactionEntity::getTransactionHash)
                .collect(Collectors.toSet());

        List<String> missingHashes = transactionHashes.stream()
                .filter(hash -> !existingHashes.contains(hash))
                .toList();

        List<EthTransactionEntity> entities = new ArrayList<>();
        if (isNotEmpty(missingHashes)) {
            List<EthTransaction> transactions = integration.findTransactionsBy(missingHashes);

            List<EthTransactionEntity> list = adapt(transactions);
            if (!list.isEmpty()) {
                entities = repository.saveAll(list);
            }
        }

        entities.addAll(existingEntities);

        return entities;
    }

    public List<EthTransactionEntity> findAllTransactions() {
        return repository.findAll();
    }

    private List<EthTransactionEntity> adapt(List<EthTransaction> transactions) {
        List<EthTransactionEntity> list = new ArrayList<>(transactions
                .stream()
                .filter(Objects::nonNull)
                .map(ethTransaction -> ethTransaction.getTransaction()
                        .map(transaction -> EthTransactionEntity.builder()
                                .input(transaction.getInput())
                                .blockNumber(String.valueOf(transaction.getBlockNumber()))
                                .blockHash(transaction.getBlockHash())
                                .value(transaction.getValueRaw())
                                .fromAddress(transaction.getFrom())
                                .toAddress(transaction.getTo())
                                .transactionHash(transaction.getHash())
                                .build())
                        .orElse(null))
                .toList());
        list.removeIf(Objects::isNull);
        return list;
    }
}