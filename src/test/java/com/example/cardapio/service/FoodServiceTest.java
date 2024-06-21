package com.example.cardapio.service;

import com.example.cardapio.dtos.FoodDTO;
import com.example.cardapio.exceptions.TitleAlreadyExistsException;
import com.example.cardapio.mapper.FoodMapper;
import com.example.cardapio.model.Food;
import com.example.cardapio.repositories.FoodRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FoodServiceTest {

    @Mock
    private FoodRepository foodRepository;

    @Mock
    private FoodMapper foodMapper;

    @InjectMocks
    private FoodService foodService;

    @Nested
    class saveFood {

        @Test
        @DisplayName("Deve criar um alimento com sucesso")
        void shouldSaveAFoodWithSuccess() {

            // Arrange

            var foodDTO = new FoodDTO(1L, "Macarrão", "http://example.com/image.jpg", 30);
            var food = new Food(1L, "Macarrão", "http://example.com/image.jpg", 30);

            doReturn(food).when(foodMapper).toEntity(any(FoodDTO.class));
            doReturn(food).when(foodRepository).save(any(Food.class));
            doReturn(new FoodDTO(1L, "Macarrão", "http://example.com/image.jpg", 30)).when(foodMapper).toDTO(any(Food.class));

            // Act
            var output = foodService.saveFood(foodDTO);

            // Assert

            // verifica se não é null
            assertNotNull(output);
            // verifica se a saída é igual ao foodDTO
            assertEquals(foodDTO.id(), output.id());
            assertEquals(foodDTO.title(), output.title());
            assertEquals(foodDTO.image(), output.image());
            assertEquals(foodDTO.price(), output.price());
        }


        @Test
        @DisplayName("Deve lançar TitleAlreadyExistsException quando o título já existir")
        void shouldThrowTitleAlreadyExistsException() {
            // Arrange
            var existingFood = new Food(1L, "Macarrão", "http://example.com/image.jpg", 30);
            var foodDTO = new FoodDTO(1L, "Macarrão", "http://example.com/image.jpg", 30);

            doReturn(Optional.of(existingFood)).when(foodRepository).findByTitle(anyString());

            // Act & Assert
            assertThrows(TitleAlreadyExistsException.class, () -> {
                foodService.saveFood(foodDTO);
            });
        }
    }
}