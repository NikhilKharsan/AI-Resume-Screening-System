package com.scaler.resumescreener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.scaler.resumescreener.repositories")
@EnableTransactionManagement
public class ResumeScreenerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ResumeScreenerApplication.class, args);
    }
}