package com.cardservice.CardService.services;

import com.cardservice.CardService.dto.CardEntityResponseDTO;
import com.cardservice.CardService.dto.CreateCardRequestDTO;
import com.cardservice.CardService.entities.CardEntity;
import com.cardservice.CardService.repositories.CardRepository;
import com.cardservice.CardService.util.CardNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
        CardEntity cardEntity = convertToCardEntity(dto);
        cardRepository.save(cardEntity);

        return convertToCardEntityResponseDTO(cardEntity);
    }

    @Transactional
    public void delete(int id) {
        cardRepository.deleteById(id);
    }

    private CardEntityResponseDTO convertToCardEntityResponseDTO(CardEntity cardEntity) {
        return modelMapper.map(cardEntity, CardEntityResponseDTO.class);
    }

    private CardEntity convertToCardEntity(CreateCardRequestDTO dto) {
        CardEntity entity = new CardEntity();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setCategory(dto.getCategory());
        entity.setCost(dto.getCost());
        entity.setStock(dto.getStock());
        entity.setSellerId(dto.getSellerId());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        return entity;
    }


}
