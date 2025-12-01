package com.cardservice.services;

import com.cardservice.dto.CardEntityResponseDTO;
import com.cardservice.dto.CreateCardRequestDTO;
import com.cardservice.entities.CardEntity;
import com.cardservice.repositories.CardRepository;
import com.cardservice.util.CardNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CardService {

    private final CardRepository cardRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CardService(CardRepository cardRepository, ModelMapper modelMapper) {
        this.cardRepository = cardRepository;
        this.modelMapper = modelMapper;
    }

    public List<CardEntityResponseDTO> findAll() {
        return cardRepository.findAll().stream()
                .map(this::convertToCardEntityResponseDTO)
                .toList();
    }

    public CardEntityResponseDTO findOne(int id) {
        return convertToCardEntityResponseDTO(cardRepository.findById(id)
                .orElseThrow(CardNotFoundException::new));
    }

    @Transactional
    public CardEntityResponseDTO createCard(CreateCardRequestDTO dto) {
        return convertToCardEntityResponseDTO(modelMapper.map(dto, CardEntity.class));
    }

    @Transactional
    public void delete(int id) {
        cardRepository.deleteById(id);
    }

    private CardEntityResponseDTO convertToCardEntityResponseDTO(CardEntity cardEntity) {
        return modelMapper.map(cardEntity, CardEntityResponseDTO.class);
    }

}
