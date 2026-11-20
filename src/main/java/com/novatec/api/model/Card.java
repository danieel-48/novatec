package com.novatec.api.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity 
@Table(name="card")

public class Card {
    @Id 
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.UUID)
    @Column(name = "card_id")
    private UUID cardId;

    @Column(name = "product_id", nullable = false, length = 6)
    private String productId;

    @Column(name = "card_number", nullable = false, unique = true, length = 16)
    private String cardNumber;

    @Column(name = "holder_name", nullable = false, length = 150)
    private String holderName;

    @Column(name = "issue_at", nullable = false)
    private LocalDate issueAT;

    @Column(name = "expires_at", nullable = false)
    private LocalDate expiresAT;

    @Column (nullable = false, length = 15)
    private String status;

    @Column(name = "blocked_at", nullable = true)
    private LocalDateTime blockedAT;

    @Column(name = "blocked_reason", nullable = true, length = 255)
    private String blockedReason;

    @Column (name = "balance", nullable = false, precision = 15, scale = 2)
    private BigDecimal balance;

    @Column (nullable = false, length = 3)
    private String currency;


    public UUID getCardId() {
        return cardId;
    }

    public void setCardId(UUID cardId) {
        this.cardId = cardId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public LocalDate getIssueAT() {
        return issueAT;
    }

    public void setIssueAT(LocalDate issueAT) {
        this.issueAT = issueAT;
    }

    public LocalDate getExpiresAT() {
        return expiresAT;
    }

    public void setExpiresAT(LocalDate expiresAT) {
        this.expiresAT = expiresAT;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getBlockedAT() {
        return blockedAT;
    }

    public void setBlockedAT(LocalDateTime blockedAT) {
        this.blockedAT = blockedAT;
    }

    public String getBlockedReason() {
        return blockedReason;
    }

    public void setBlockedReason(String blockedReason) {
        this.blockedReason = blockedReason;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
