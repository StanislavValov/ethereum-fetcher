package com.limechain.etherium.domain.service;

import com.limechain.etherium.domain.entity.UserSearchHistoryEntity;
import com.limechain.etherium.domain.repository.UserSearchHistoryRepository;
import com.limechain.etherium.utils.RlpUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserHistoryService {
    private UserSearchHistoryRepository historyRepository;

    //TODO: remove transactional, use dynamicEntityGraph to avoid N+1 and other issues
    @Transactional
    public List<UserSearchHistoryEntity> getUserSearchesAsRlp(String username){
        List<UserSearchHistoryEntity> entities = historyRepository.findByUsernameOrderBySearchedAtDesc(username);

        entities.stream().map(UserSearchHistoryEntity::getTransactionHashSet).map(RlpUtil::mapToRlpHex);
        return entities;
    }

    @Transactional
    public void storeUserSearchHistory(String username, String[] transactionHashes) {
        UserSearchHistoryEntity entity = adapt(username, transactionHashes);
        historyRepository.save(entity);
    }

    private UserSearchHistoryEntity adapt(String username, String[] transactionHashes) {
        Set<String> transactionHash = Set.of(transactionHashes);
        return UserSearchHistoryEntity.builder()
                .searchedAt(LocalDateTime.now())
                .username(username)
                .transactionHashSet(transactionHash)
                .build();
    }
}
