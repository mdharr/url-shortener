package com.mdharr.urlshortener.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class HashUnknown extends RuntimeException {
    public HashUnknown(String message) {
        super(message);
    }
}
