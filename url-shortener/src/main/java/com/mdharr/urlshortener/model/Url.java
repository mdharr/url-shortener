package com.mdharr.urlshortener.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "urls")
public class Url implements Serializable {

    private static final long serializable = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String shortUrl;

    @Column(nullable = false, length = 2048)
    private String longUrl;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Url() {
    }

    public Url(String shortUrl, String longUrl) {
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Url url = (Url) o;
        return Objects.equals(id, url.id) && Objects.equals(shortUrl, url.shortUrl) && Objects.equals(longUrl, url.longUrl) && Objects.equals(createdAt, url.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shortUrl, longUrl, createdAt);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Url{");
        sb.append("id=").append(id);
        sb.append(", shortUrl='").append(shortUrl).append('\'');
        sb.append(", longUrl='").append(longUrl).append('\'');
        sb.append(", createdAt=").append(createdAt);
        sb.append('}');
        return sb.toString();
    }
}
