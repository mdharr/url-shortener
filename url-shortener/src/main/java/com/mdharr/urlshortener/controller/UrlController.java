package com.mdharr.urlshortener.controller;

import com.mdharr.urlshortener.dto.UrlRequest;
import com.mdharr.urlshortener.dto.UrlResponse;
import com.mdharr.urlshortener.service.UrlService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api")
public class UrlController {

    private UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shorten")
    public UrlResponse shorten(@Valid @RequestBody UrlRequest request) {
        String hash = urlService.shorten(request.url());
        return new UrlResponse(hash);
    }

}
