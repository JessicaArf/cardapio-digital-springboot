package com.example.cardapio.controller;

import com.example.cardapio.dtos.FoodDTO;
import com.example.cardapio.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("food")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @PostMapping
    public ResponseEntity<FoodDTO> saveFood(@RequestBody FoodDTO data){
        FoodDTO food = foodService.saveFood(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(food);
    }

    @GetMapping
    public ResponseEntity<List<FoodDTO>> getAll(){
        List<FoodDTO> foodList = foodService.getAllFood();
        return ResponseEntity.status(HttpStatus.OK).body(foodList);
    }

}
