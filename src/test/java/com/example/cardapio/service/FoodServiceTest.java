package com.example.cardapio.service;

import com.example.cardapio.dtos.FoodDTO;
import com.example.cardapio.exceptions.FoodNotFoundException;
import com.example.cardapio.exceptions.TitleAlreadyExistsException;
import com.example.cardapio.mapper.FoodMapper;
import com.example.cardapio.model.Food;
import com.example.cardapio.repositories.FoodRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FoodServiceTest {

    @Mock
    private FoodRepository foodRepository;

    @Mock
    private FoodMapper foodMapper;

    @InjectMocks
    private FoodService foodService;

    @Captor
    private ArgumentCaptor<Food> foodArgumentCaptor;
    @Captor
    private ArgumentCaptor<FoodDTO> foodDtoArgumentCaptor;

    @Nested
    class saveFood {

        @Test
        @DisplayName("Deve criar um alimento com sucesso")
        void shouldSaveAFoodWithSuccess() {

            // Arrange

            var foodDTO = new FoodDTO(1L, "Macarrão", "http://example.com/image.jpg", 30);
            var food = new Food(1L, "Macarrão", "http://example.com/image.jpg", 30);

            doReturn(food).when(foodMapper).toEntity(foodDtoArgumentCaptor.capture());
            doReturn(food).when(foodRepository).save(foodArgumentCaptor.capture());
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
        @DisplayName("Deve lançar TitleAlreadyExistsException quando o título já existir.")
        void shouldThrowTitleAlreadyExistsException() {
            // Arrange
            var existingFood = new Food(1L, "Macarrão", "http://example.com/image.jpg", 30);
            var foodDTO = new FoodDTO(1L, "Macarrão", "http://example.com/image.jpg", 30);

            doReturn(Optional.of(existingFood)).when(foodRepository).findByTitle("Macarrão");

            // Act & Assert
            assertThrows(TitleAlreadyExistsException.class, () -> {
                foodService.saveFood(foodDTO);
            });
        }
    }

    @Nested
    class getAllFood {

        @Test
        @DisplayName("Deve retornar a lista de alimentos com sucesso.")
        void shouldReturnAllFoodsWithSuccess() {
            var food = new Food(1L, "Macarrão", "http://example.com/image.jpg", 30);
            var foodDTO = new FoodDTO(1L, "Macarrão", "http://example.com/image.jpg", 30);

            var foodList = List.of(food);
            var foodDTOList = List.of(foodDTO);

            doReturn(foodList).when(foodRepository).findAll();
            doReturn(foodDTO).when(foodMapper).toDTO(foodArgumentCaptor.capture());

            // Act
            var output = foodService.getAllFoods();

            // Assert
            assertNotNull(output);
            assertEquals(foodDTOList.size(), output.size());

            // Verifica se os elementos da lista são iguais
            assertEquals(foodDTOList.get(0).id(), output.get(0).id());
            assertEquals(foodDTOList.get(0).title(), output.get(0).title());
            assertEquals(foodDTOList.get(0).image(), output.get(0).image());
            assertEquals(foodDTOList.get(0).price(), output.get(0).price());
        }

    }

    @Nested
    class getFoodById {

        @Test
        @DisplayName("Retornar um usuário por id com sucesso.")
        void shouldGetFoodByIdWithSuccess() {

            // Arrange
            Long foodId = 1L;
            Food existingFood = new Food(foodId, "Macarrão", "http://example.com/image.jpg", 30);
            FoodDTO expectedFoodDTO = new FoodDTO(foodId, "Macarrão", "http://example.com/image.jpg", 30);

            // Configura o comportamento do repositório para retornar um Optional com o Food
            when(foodRepository.findById(foodId)).thenReturn(Optional.of(existingFood));
            // Configura o comportamento do mapeador para converter o Food em FoodDTO
            when(foodMapper.toDTO(existingFood)).thenReturn(expectedFoodDTO);

            // Act
            FoodDTO actualFoodDTO = foodService.getFoodById(foodId);

            // Assert
            assertNotNull(actualFoodDTO);
            assertEquals(expectedFoodDTO.id(), actualFoodDTO.id());
            assertEquals(expectedFoodDTO.title(), actualFoodDTO.title());
            assertEquals(expectedFoodDTO.image(), actualFoodDTO.image());
            assertEquals(expectedFoodDTO.price(), actualFoodDTO.price());

        }

        @Test
        @DisplayName("Deve lançar FoodNotFoundException quando não encontrar o ID.")
        void shouldThrowFoodNotFoundException() {

            Long nonExistentId = 1L;

            when(foodRepository.findById(nonExistentId)).thenReturn(Optional.empty());

            FoodNotFoundException exception = assertThrows(FoodNotFoundException.class, () -> {
                foodService.getFoodById(nonExistentId);
            });

            assertThrows(FoodNotFoundException.class, () -> {
                foodService.getFoodById(nonExistentId);
            });

        }
    }

    @Nested
    class deleteFood {
        @Test
        @DisplayName("Deve deletar um alimento pelo ID.")
        void shouldDeleteFoodByIdWithSuccess() {
            // Arrange
            Long foodId = 1L;
            Food existingFood = new Food(foodId, "Macarrão", "http://example.com/image.jpg", 30);
            doReturn(Optional.of(existingFood)).when(foodRepository).findById(foodId);

            // Act
            foodService.deleteFood(foodId);

            // Assert
            verify(foodRepository, times(1)).delete(existingFood);
        }

        @Test
        @DisplayName("Deve lançar FoodNotFoundException quando não encontrar o ID.")
        void shouldThrowFoodNotFoundException() {

            Long nonExistentId = 1L;

            when(foodRepository.findById(nonExistentId)).thenReturn(Optional.empty());

            FoodNotFoundException exception = assertThrows(FoodNotFoundException.class, () -> {
                foodService.getFoodById(nonExistentId);
            });

            assertThrows(FoodNotFoundException.class, () -> {
                foodService.deleteFood(nonExistentId);
            });

        }
    }

    @Nested
    class updateFood {

        @Test
        @DisplayName("Atualizar o alimento com sucesso.")
        void shouldUpdateFoodWithSuccess() {
            // Arrange
            Long foodId = 1L;
            var updateFoodDTO = new FoodDTO(foodId, "Novo Macarrão", "http://example.com/newimage.jpg", 35);
            var existingFood = new Food(foodId, "Macarrão", "http://example.com/image.jpg", 30);

            doReturn(Optional.of(existingFood)).when(foodRepository).findById(foodId);
            doReturn(existingFood).when(foodRepository).save(foodArgumentCaptor.capture());
            doReturn(updateFoodDTO).when(foodMapper).toDTO(foodArgumentCaptor.capture());

            // Act
            foodService.updateFood(foodId, updateFoodDTO);

            // Assert
            verify(foodRepository).findById(foodId);
            verify(foodRepository).save(foodArgumentCaptor.capture());

            var foodCaptured = foodArgumentCaptor.getValue();

            assertEquals(updateFoodDTO.id(), foodCaptured.getId());
            assertEquals(updateFoodDTO.title(), foodCaptured.getTitle());
            assertEquals(updateFoodDTO.price(), foodCaptured.getPrice());
            assertEquals(updateFoodDTO.image(), foodCaptured.getImage());
        }

        @Test
        @DisplayName("Deve lançar FoodNotFoundException quando não encontrar o ID")
        void shouldThrowFoodNotFoundException() {
            Long nonExistentId = 1L;
            FoodDTO updateData = new FoodDTO(nonExistentId, "Macarrão à Bolonhesa", "http://example.com/image.jpg", 35);

            when(foodRepository.findById(nonExistentId)).thenReturn(Optional.empty());

            assertThrows(FoodNotFoundException.class, () -> foodService.updateFood(nonExistentId, updateData));
        }
    }
}