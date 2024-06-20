package com.example.cardapio.repositories;

import com.example.cardapio.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface FoodRepository extends JpaRepository<Food, Long> {
    Optional<Food> findByTitle(String title);
}
