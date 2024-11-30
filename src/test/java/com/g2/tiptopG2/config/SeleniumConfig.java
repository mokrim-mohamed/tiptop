package com.g2.tiptopG2.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeleniumConfig {

    @Bean
    public WebDriver webDriver() {
        // Configure automatiquement le WebDriver pour Chrome
        WebDriverManager.chromedriver().setup();

        // Configuration des options pour Chrome
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Mode headless (sans interface graphique)
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu"); // Optionnel, peut être utile selon l'environnement

        // Retourne une instance de ChromeDriver avec les options configurées
        return new ChromeDriver(options);
    }
}
