package com.cardservice.CardService.controllers;

import com.cardservice.CardService.dto.CardEntityResponseDTO;
import com.cardservice.CardService.dto.CreateCardRequestDTO;
import com.cardservice.CardService.services.CardService;
import com.cardservice.CardService.util.CardErrorResponse;
import com.cardservice.CardService.util.CardNotFoundException;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping()
    public List<CardEntityResponseDTO> index() {
        return cardService.findAll();
    }

    @GetMapping("/{id}")
    public CardEntityResponseDTO show(@PathVariable("id") int id) {
        return cardService.findOne(id);
    }

    @PostMapping()
    public ResponseEntity<CardEntityResponseDTO> createCard(@RequestBody CreateCardRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cardService.createCard(dto));
    }

    @ExceptionHandler
    public ResponseEntity<CardErrorResponse> handleException(CardNotFoundException e) {
        CardErrorResponse cardErrorResponse = new CardErrorResponse(
                "Card with that id is not found",
                LocalDateTime.now()
        );

        return new ResponseEntity<>(cardErrorResponse, HttpStatus.NOT_FOUND);
    }
}
