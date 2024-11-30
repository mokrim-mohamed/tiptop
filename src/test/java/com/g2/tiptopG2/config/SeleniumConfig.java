package com.g2.tiptopG2.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeleniumConfig {

    @Bean
    public WebDriver webDriver() {
        // Configuration automatique du WebDriver pour Chrome
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }
}
