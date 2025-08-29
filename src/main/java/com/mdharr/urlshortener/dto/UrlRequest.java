package com.mdharr.urlshortener.dto;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

public record UrlRequest(
        @NotNull(message = "URL cannot be null")
        @URL(message = "Invalid URL format")
        String url
) { }
