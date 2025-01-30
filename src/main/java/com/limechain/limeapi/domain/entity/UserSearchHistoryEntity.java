package com.limechain.limeapi.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "user_search_history")
@NoArgsConstructor
@Builder
@Getter
@AllArgsConstructor
public class UserSearchHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "username", nullable = false)
    private String username;

    @ElementCollection
    @CollectionTable(name = "user_search", joinColumns = @JoinColumn(name = "user_search_history_id"))
    @Column(name = "transaction_hash", nullable = false)
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST) // Ensure parent persists first
    private Set<String> transactionHashSet;

    @Column(nullable = false)
    private LocalDateTime searchedAt;
}