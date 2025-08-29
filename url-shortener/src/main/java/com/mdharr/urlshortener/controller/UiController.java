package com.mdharr.urlshortener.controller;

import com.mdharr.urlshortener.service.UrlService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UiController {

    private final UrlService urlService;

    public UiController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/")
    public String showForm() {
        return "index";
    }

    @PostMapping("/shorten")
    public String shortenUrl(@RequestParam("url") String longUrl, Model model) {
        String shortUrl = urlService.shorten(longUrl);
        model.addAttribute("shortenedUrl", "http://localhost:8080/" + shortUrl);
        return "index";
    }
}
