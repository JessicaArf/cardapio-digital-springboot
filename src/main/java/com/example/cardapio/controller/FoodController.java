package com.example.cardapio.controller;

import com.example.cardapio.dtos.FoodDTO;
import com.example.cardapio.service.FoodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("food")
@RequiredArgsConstructor
@Validated
public class FoodController {

    private final FoodService foodService;

    @PostMapping
    public ResponseEntity<FoodDTO> saveFood(@RequestBody @Valid FoodDTO data){
        FoodDTO food = foodService.saveFood(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(food);
    }

    @GetMapping
    public ResponseEntity<List<FoodDTO>> getAll(){
        List<FoodDTO> foodList = foodService.getAllFood();
        return ResponseEntity.status(HttpStatus.OK).body(foodList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodDTO> getFoodById(@PathVariable Long id){
        FoodDTO food = foodService.getFoodById(id);
        return ResponseEntity.status(HttpStatus.OK).body(food);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FoodDTO> updateFood(@PathVariable Long id, @RequestBody FoodDTO data){
        FoodDTO food = foodService.updateFood(id, data);
        return ResponseEntity.status(HttpStatus.OK).body(food);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFood(@PathVariable Long id){
        foodService.deleteFood(id);
        return ResponseEntity.status(HttpStatus.OK).body("Food deleted successfully.");
    }

}
