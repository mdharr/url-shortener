package com.mdharr.urlshortener.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Duration;

@Component
public class RateLimitingInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(RateLimitingInterceptor.class);
    private final RedisTemplate<String, String> redisTemplate;
    private static final int MAX_REQUESTS = 10;
    private static final long TIME_WINDOW_SECONDS = 60;

    public RateLimitingInterceptor(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ipAddress = getClientIp(request);
        String key = "rate_limit:" + ipAddress;

        Long currentCount = redisTemplate.opsForValue().increment(key, 1);

        if (currentCount == null) {
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            return false;
        }

        if (currentCount == 1) {
            redisTemplate.expire(key, Duration.ofSeconds(TIME_WINDOW_SECONDS));
        }

        if (currentCount > MAX_REQUESTS) {
            log.warn("Client with IP: {}, has reached the max requests of: {}", ipAddress, currentCount);
            response.setStatus(429);
            response.getWriter().write("You have exceeded the API request limit. Please try again later.");
            return false;
        }

        return true;
    }

    private String getClientIp(HttpServletRequest request) {
        String xForwardedForHeader = request.getHeader("X-Forwarded-For");
        if (xForwardedForHeader != null) {
            return xForwardedForHeader.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
