package com.mdharr.urlshortener.repository;

import com.mdharr.urlshortener.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {

    Optional<Url> findByShortUrl(String shortUrl);
    Optional<Url> findByLongUrl(String longUrl);
}
