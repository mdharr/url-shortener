package com.mdharr.urlshortener.service;

import com.mdharr.urlshortener.exception.HashUnknown;
import com.mdharr.urlshortener.model.Url;
import com.mdharr.urlshortener.repository.UrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.Optional;

@Service
public class UrlService {
    private static final Logger log = LoggerFactory.getLogger(UrlService.class);
    private final UrlRepository urlRepository;
    private final RedisTemplate<String, String> redis;
    private static final MessageDigest DIGEST;

    static {
        try {
            DIGEST = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available.", e);
        }
    }

    public UrlService(UrlRepository urlRepository, RedisTemplate<String, String> redis) {
        this.urlRepository = urlRepository;
        this.redis = redis;
    }

    public String shorten(String url) {
        Optional<Url> existingUrl = urlRepository.findByLongUrl(url);

        if (existingUrl.isPresent()) {
            log.info("User requested to shorten an existing link. Returning existing hash.");
            return existingUrl.get().getShortUrl();
        }

        String hash = hash(url, 6);
        Url urlEntity = new Url(hash, url);
        urlRepository.save(urlEntity);
        redis.opsForValue().set(hash, url, Duration.ofMinutes(30));

        return hash;
    }

    private String hash(String url, int length) {
        byte[] bytes = DIGEST.digest(url.getBytes());
        String hash = String.format("%32x", new BigInteger(1, bytes));
        return hash.substring(0, Math.min(length, hash.length()));
    }

    public String resolve(String hash) {
        String url = redis.opsForValue().get(hash);

        if (url != null) {
            System.out.println("Serving from cache: " + url);
            log.info("Serving from cache: {}", url);
            return url;
        }

        Optional<Url> urlEntity = urlRepository.findByShortUrl(hash);

        if (urlEntity.isPresent()) {
            String longUrl = urlEntity.get().getLongUrl();
            redis.opsForValue().set(hash, longUrl, Duration.ofMinutes(30));
            log.info("Serving from database and rehydrating cache: {}", longUrl);
            return longUrl;
        }

        log.warn("Hash not found in database: {}", hash);
        throw new HashUnknown("Hash not found: " + hash);
    }

}
