package com.library.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * AppConfig - Main Spring application configuration.
 *
 * Responsibilities:
 * - Enables component scanning for all com.library packages
 *   (discovering controllers, services, DAOs, and configuration classes)
 * - Enables Spring transaction management (@Transactional)
 */
@Configuration
@ComponentScan(basePackages = "com.library")
@EnableTransactionManagement
public class AppConfig {
    // Spring will automatically discover all @Service, @Repository,
    // @Controller, and @Configuration classes under com.library
}
