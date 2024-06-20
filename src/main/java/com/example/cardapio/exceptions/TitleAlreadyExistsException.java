package com.example.cardapio.exceptions;

public class TitleAlreadyExistsException extends RuntimeException{

    public TitleAlreadyExistsException(){
        super("Food with title already exists.");
    }

    public TitleAlreadyExistsException(String message){
        super(message);
    }

    public TitleAlreadyExistsException(String message, Throwable cause){
        super(message, cause);
    }

}
