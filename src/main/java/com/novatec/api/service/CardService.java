package com.novatec.api.service;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novatec.api.repository.CardRepository;
import com.novatec.api.repository.ProductRepository;


import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.novatec.api.dto.CardStatusResponse;
import com.novatec.api.model.Card;
import com.novatec.api.dto.CardIssuedResponse;
import com.novatec.api.dto.BlockCardRequest;
import com.novatec.api.dto.CardDetailResponse;

import com.novatec.api.dto.CardDto;


@Service
public class CardService {
    
    // Additional methods for CardService can be added here
    
    private final ProductRepository productRepository;
    private final CardRepository cardRepository;
    private final SecureRandom secureRandom = new SecureRandom();

    public CardService(CardRepository cardRepository, ProductRepository productRepository) {
        this.cardRepository = cardRepository;
        this.productRepository = productRepository;
    }

    private String generateUniqueCardNumber(String productId) {
        String candidate;
        do {
            StringBuilder suffix = new StringBuilder(10);

            for (int i = 0; i < 10; i++) {
                suffix.append(secureRandom.nextInt(10));
            }
            candidate = productId + suffix.toString();
        } while (cardRepository.existsByCardNumber(candidate));
        return candidate;
    }

    @Transactional
    public CardIssuedResponse issueCard(CardDto request) {
        productRepository.findById(request.productId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));

            String cardNumber = generateUniqueCardNumber(request.productId());
            LocalDate issueAT = LocalDate.now();
            LocalDate expiresAT = issueAT.plusYears(3);
           
            
            Card card = new Card();
            card.setProductId(request.productId());
            card.setCardNumber(cardNumber);
            card.setHolderName(request.holderName());
            card.setIssueAT(issueAT);
            card.setExpiresAT(expiresAT);
            card.setStatus("ISSUED");
            card.setBalance(BigDecimal.ZERO.setScale(2));
            card.setCurrency("USD");

            Card saved = cardRepository.save(card);

            return new CardIssuedResponse(

                saved.getCardId(),
                saved.getCardNumber(),
                saved.getStatus(),
                saved.getExpiresAT(),
                saved.getBalance(),
                saved.getCurrency()
            );
    }

    @Transactional 
    public CardStatusResponse activateCard(UUID cardId) {
        Card card = findCardOrThrow(cardId);

        switch (card.getStatus()) {


            case "ACTIVE" ->{
                return new CardStatusResponse(card.getCardId(), card.getStatus());
            }
            case "ISSUED" ->{
                card.setStatus("ACTIVE");
                Card updated = cardRepository.save(card);
                return new CardStatusResponse(updated.getCardId(), updated.getStatus());
            }
            case "BLOCKED" -> throw new ResponseStatusException(
                HttpStatus.CONFLICT, "No se puede activar una tarjeta bloqueada"
            );
            default -> throw new ResponseStatusException(
                HttpStatus.CONFLICT, "Estado invalido para activar la tarjeta"
            );
        }
    }

    @Transactional 
    public CardStatusResponse blockCard(UUID cardId, BlockCardRequest request) {
        Card card = findCardOrThrow(cardId);
        LocalDateTime lockedAT = LocalDateTime.now();

        if("BLOCKED".equals(card.getStatus())){
            return new CardStatusResponse(card.getCardId(), card.getStatus());
        }
        
        card.setStatus("BLOCKED");
        card.setBlockedAT(lockedAT);
        if(request != null && request.reason() != null && !request.reason().isBlank()){
            card.setBlockedReason(request.reason().trim());
        }

        Card updated = cardRepository.save(card);
        return new CardStatusResponse(updated.getCardId(), updated.getStatus());

    }

    @Transactional(readOnly = true)
    public CardDetailResponse getCard(UUID cardId) {
        Card card = findCardOrThrow(cardId);

        String blockedAtStr = card.getBlockedAT() != null ? card.getBlockedAT().toString() : "";
        String blockedReasonStr = card.getBlockedReason() != null ? card.getBlockedReason() : "";


        return new CardDetailResponse(
           card.getCardId(),
           card.getCardNumber(),
           card.getHolderName(),
           card.getStatus(),
           blockedAtStr,
           blockedReasonStr,
           card.getExpiresAT(),
           card.getBalance(),
           card.getCurrency()
        );
    }

    private Card findCardOrThrow(UUID cardId) {
        return cardRepository.findById(cardId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarjeta no encontrada"));
    }
}
