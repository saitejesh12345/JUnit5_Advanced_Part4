package com.example.Mobile_app_QuickStart.exception;

public class ResourceNotFoundException extends RuntimeException{


    //Constructor with single parameter
    public ResourceNotFoundException(String message){
        super(message);
    }

    //constructor with two parameters
    public ResourceNotFoundException(String message,Throwable cause){
        super(message,cause);
    }
}
