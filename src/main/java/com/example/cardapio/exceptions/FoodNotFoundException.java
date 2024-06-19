package com.example.cardapio.exceptions;

public class FoodNotFoundException extends RuntimeException{
    public FoodNotFoundException(Long id){
        super("Food with id: " + id + " not found.");
    }

    public FoodNotFoundException(String message){
        super(message);
    }

    public FoodNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
