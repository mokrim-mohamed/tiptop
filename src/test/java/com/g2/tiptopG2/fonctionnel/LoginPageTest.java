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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginPageTest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();

        // Configuration des options de Chrome
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://test.wk-archi-o23-15m-g2.fr/login");

        // Attente que la page se charge
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h2"))); // Attendre l'apparition de "Bienvenue"
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void testLoginSuccess() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Vérification que la page contient le texte "Bienvenue"
        WebElement welcomeElement = driver.findElement(By.tagName("h2"));
        assertTrue(welcomeElement.getText().contains("Bienvenue"), "Le texte 'Bienvenue' est absent");

        // Remplir le champ username
        WebElement usernameField = driver.findElement(By.id("username"));
        usernameField.sendKeys("test@gmail.com");

        // Remplir le champ password
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("mokrim1234");

        // Soumettre le formulaire
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
        loginButton.click();

        // Vérification de la redirection
        wait.until(ExpectedConditions.urlContains("admin/dashboard")); // Redirection attendue
        assertTrue(driver.getCurrentUrl().contains("admin/dashboard"), "Redirection vers le tableau de bord échouée.");
    }

    @Test
    void testLoginFailure() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Remplir le champ username avec des données incorrectes
        WebElement usernameField = driver.findElement(By.id("username"));
        usernameField.sendKeys("wronguser@gmail.com");

        // Remplir le champ password avec des données incorrectes
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("wrongpassword");

        // Soumettre le formulaire
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
        loginButton.click();

        // Vérification du message d'erreur
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[style='color: red;'] p")));
        assertTrue(errorMessage.getText().contains("Invalid username or password."), "Le message d'erreur attendu est absent.");
    }
}
