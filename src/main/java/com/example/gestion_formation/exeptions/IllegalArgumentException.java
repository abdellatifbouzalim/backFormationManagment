package com.example.gestion_formation.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


    @ResponseStatus(HttpStatus.CONFLICT)
    public class IllegalArgumentException extends  RuntimeException {

        public IllegalArgumentException(String message) {
            super(message);
        }
    }