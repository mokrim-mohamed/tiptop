package com.g2.tiptopG2.fonctionnel;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReglementPageTest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        driver.get("https://www.dsp5-archi-o23-15m-g2.fr/reglement");

        // Attente explicite
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void testReglementPageTitle() {
        // Vérifie que le titre de la page est bien "Règlement - Thé Tip Top - Conditions du jeu concours"
        String expectedTitle = "Règlement - Thé Tip Top - Conditions du jeu concours";
        String actualTitle = driver.getTitle();
        assertEquals(expectedTitle, actualTitle);
    }

    @Test
    void testReglementPageContent() {
        // Vérifie la présence de certaines sections importantes du règlement
        WebElement reglementTitle = driver.findElement(By.cssSelector(".reglement-title"));
        assertEquals("Règlement du Jeu Concours", reglementTitle.getText());

        WebElement firstRule = driver.findElement(By.xpath("//ul/li[1]"));
        assertEquals("Règles", firstRule.getText());
    }
}
