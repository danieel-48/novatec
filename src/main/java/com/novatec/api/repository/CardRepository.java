package com.novatec.api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.novatec.api.model.Card;

public interface CardRepository extends JpaRepository<Card, UUID> {
    boolean existsByCardNumber(String cardNumber);
}
