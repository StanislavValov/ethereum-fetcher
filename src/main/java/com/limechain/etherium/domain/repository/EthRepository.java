package com.limechain.etherium.domain.repository;

import com.limechain.etherium.domain.entity.EthTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EthRepository extends JpaRepository<EthTransactionEntity, Long> {
    List<EthTransactionEntity> findAllByTransactionHashIn(List<String> transactionHashes);
}
