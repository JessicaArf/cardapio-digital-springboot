package com.example.cardapio.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FoodDTO(Long id,
                      @NotBlank(message = "O título não pode estar em branco.")
                      String title,
                      @NotBlank(message = "A imagem não pode estar em branco.")
                      String image,
                      @NotNull(message = "O preço não pode estar em branco.")
                      Integer price) {
}
