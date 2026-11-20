package com.novatec.api.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.novatec.api.dto.BlockCardRequest;
import com.novatec.api.dto.CardDetailResponse;
import com.novatec.api.dto.CardDto;
import com.novatec.api.dto.CardIssuedResponse;
import com.novatec.api.dto.CardStatusResponse;
import com.novatec.api.service.CardService;

import jakarta.validation.Valid;

@RestController 
@RequestMapping ("/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

   @PostMapping 
   public ResponseEntity<CardIssuedResponse> issueCard(@Valid @RequestBody CardDto request) {
       return ResponseEntity.status(HttpStatus.CREATED).body(cardService.issueCard(request));
   }

   @PostMapping("/{cardId}/activate")
   public ResponseEntity<CardStatusResponse> activateCard(@PathVariable UUID cardId) {
       return ResponseEntity.ok().body(cardService.activateCard(cardId));
   }

   @PostMapping("/{cardId}/block")
   public ResponseEntity<CardStatusResponse> blockCard(@PathVariable UUID cardId, @Valid @RequestBody (required=false) BlockCardRequest request) {
       return ResponseEntity.ok().body(cardService.blockCard(cardId, request));
   }

   @GetMapping("/{cardId}")
   public ResponseEntity<CardDetailResponse> getCard(@PathVariable UUID cardId) {
       return ResponseEntity.ok().body(cardService.getCard(cardId));
   }
}
