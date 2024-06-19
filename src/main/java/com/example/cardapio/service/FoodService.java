package com.example.cardapio.service;

import com.example.cardapio.dtos.FoodDTO;
import com.example.cardapio.mapper.FoodMapper;
import com.example.cardapio.model.Food;
import com.example.cardapio.repositories.FoodRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodMapper foodMapper;
    private final FoodRepository foodRepository;

    public FoodDTO saveFood(FoodDTO data){
       Food food = getFood(data);
       foodRepository.save(food);
       return getFoodDto(food);
    }

    public List<FoodDTO> getAllFood(){
     List<Food> foodList = foodRepository.findAll();
     return foodMapper.toDTOList(foodList);
    }

    public FoodDTO getFoodById(Long id){
        Food food = foodRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Food not found."));
        return getFoodDto(food);
    }

    public void deleteFood(Long id){
        Food food = foodRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Food not found."));
        foodRepository.delete(food);
    }

    public FoodDTO getFoodDto(Food food){
        return foodMapper.toDTO(food);
    }

    public Food getFood(FoodDTO data){
        return foodMapper.toEntity(data);
    }

}
