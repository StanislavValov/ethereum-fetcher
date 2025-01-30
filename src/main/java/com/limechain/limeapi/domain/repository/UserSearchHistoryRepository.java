package com.limechain.limeapi.domain.repository;

import com.limechain.limeapi.domain.entity.UserSearchHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserSearchHistoryRepository extends JpaRepository<UserSearchHistoryEntity, UUID> {
    @Query("SELECT u FROM UserSearchHistoryEntity u LEFT JOIN FETCH u.transactionHashSet WHERE u.username = :username ORDER BY u.searchedAt DESC")
    List<UserSearchHistoryEntity> findByUsernameOrderBySearchedAtDesc(@Param("username") String username);
}