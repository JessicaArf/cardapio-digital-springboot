package com.example.cardapio.mapper;

import com.example.cardapio.dtos.FoodDTO;
import com.example.cardapio.model.Food;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FoodMapper {
    FoodDTO toDTO(Food food);
    Food toEntity(FoodDTO foodDto);
    List<FoodDTO> toDTOList(List<Food> foodList);
}
