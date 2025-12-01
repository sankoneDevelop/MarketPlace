package com.cardservice.CardService.repositories;

import com.cardservice.CardService.entities.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, Integer> {
}
