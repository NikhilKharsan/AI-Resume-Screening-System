package com.scaler.resumescreener.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/health")
@RequiredArgsConstructor
@Slf4j
public class HealthController {

    private final DataSource dataSource;
//    private final RedisTemplate<String, Object> redisTemplate;
//    private final RabbitTemplate rabbitTemplate;

    @GetMapping("/detailed")
    public ResponseEntity<Map<String, Object>> detailedHealth() {
        Map<String, Object> health = new HashMap<>();

        try {
            // Database Health
            health.put("database", checkDatabaseHealth());

            // Redis Health
//            health.put("redis", checkRedisHealth());

            // RabbitMQ Health
//            health.put("rabbitmq", checkRabbitMQHealth());

            // AI Service Health
            health.put("ai_service", checkAIServiceHealth());

            health.put("status", "UP");
            health.put("timestamp", Instant.now());

            return ResponseEntity.ok(health);

        } catch (Exception e) {
            log.error("Health check failed", e);
            health.put("status", "DOWN");
            health.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(health);
        }
    }

    private Map<String, String> checkDatabaseHealth() {
        Map<String, String> dbHealth = new HashMap<>();
        try (Connection connection = dataSource.getConnection()) {
            dbHealth.put("status", "UP");
            dbHealth.put("database", connection.getMetaData().getDatabaseProductName());
        } catch (Exception e) {
            dbHealth.put("status", "DOWN");
            dbHealth.put("error", e.getMessage());
        }
        return dbHealth;
    }

//    private Map<String, String> checkRedisHealth() {
//        Map<String, String> redisHealth = new HashMap<>();
//        try {
//            redisTemplate.opsForValue().set("health:check", "ping", Duration.ofSeconds(10));
//            String response = (String) redisTemplate.opsForValue().get("health:check");
//            redisHealth.put("status", "ping".equals(response) ? "UP" : "DOWN");
//        } catch (Exception e) {
//            redisHealth.put("status", "DOWN");
//            redisHealth.put("error", e.getMessage());
//        }
//        return redisHealth;
//    }

//    private Map<String, String> checkRabbitMQHealth() {
//        Map<String, String> rabbitHealth = new HashMap<>();
//        try {
//            rabbitTemplate.convertAndSend("health.check", "ping");
//            rabbitHealth.put("status", "UP");
//        } catch (Exception e) {
//            rabbitHealth.put("status", "DOWN");
//            rabbitHealth.put("error", e.getMessage());
//        }
//        return rabbitHealth;
//    }

    private Map<String, String> checkAIServiceHealth() {
        Map<String, String> aiHealth = new HashMap<>();
        // Simple health check - could be enhanced to actually call Gemini API
        aiHealth.put("status", "UP");
        aiHealth.put("service", "Gemini 1.5 Flash");
        return aiHealth;
    }
}
