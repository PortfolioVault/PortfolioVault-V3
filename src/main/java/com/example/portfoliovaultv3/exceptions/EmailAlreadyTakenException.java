package com.example.portfoliovaultv3.exceptions;

public class EmailAlreadyTakenException extends Exception{
    public EmailAlreadyTakenException() {
        super("Email already taken");
    }
}
