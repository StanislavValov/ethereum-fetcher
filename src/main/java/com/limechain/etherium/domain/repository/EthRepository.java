package com.limechain.etherium.domain.repository;

import com.limechain.etherium.domain.entity.EthTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EthRepository extends JpaRepository<EthTransactionEntity, UUID> {
    List<EthTransactionEntity> findAllByTransactionHashIn(List<String> transactionHashes);
}
