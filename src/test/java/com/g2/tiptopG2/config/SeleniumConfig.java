package com.g2.tiptopG2.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.openqa.selenium.chrome.ChromeOptions;

@Configuration
public class SeleniumConfig {

    @Bean
    public WebDriver webDriver() {
        // Configuration automatique du WebDriver pour Chrome
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        
        // Options pour exécuter Chrome en mode headless (sans interface graphique)
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");  // Nécessaire pour le mode headless sur certains systèmes
        options.addArguments("--window-size=1920x1080");  // Définit la taille de la fenêtre
        options.addArguments("--no-sandbox");  // Nécessaire dans certains environnements (par exemple, dans Docker)
        options.addArguments("--disable-dev-shm-usage");  // Permet d'éviter des erreurs de mémoire partagée dans certains environnements
        options.addArguments("--remote-debugging-port=9222");  // Débogage à distance, utile pour les logs et diagnostics
        
        // Retourne une nouvelle instance de ChromeDriver avec les options spécifiées
        return new ChromeDriver(options);
    }
}
