package com.g2.tiptopG2.fonctionnel;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginPageTest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        // Configuration du WebDriver pour Chrome
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://test.wk-archi-o23-15m-g2.fr/login");

        // Attente que la page se charge
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h2"))); // Attendre l'apparition de l'élément "Bienvenue"
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void testLoginSuccess() {
        // Attendre que le champ username soit visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Vérifier que la page contient le texte "Bienvenue"
        WebElement welcomeElement = driver.findElement(By.tagName("h2"));
        assertTrue(welcomeElement.getText().contains("Bienvenue"));

        // Remplir le champ username
        WebElement usernameField = driver.findElement(By.id("username"));
        usernameField.sendKeys("test@gmail.com");

        // Remplir le champ password
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("mokrim1234");

        // Soumettre le formulaire
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
        loginButton.click();

        // Vérifier la redirection ou la présence d'un message d'erreur
        wait.until(ExpectedConditions.urlContains("admin/dashboard")); // Vérifie si la redirection a eu lieu vers la page d'administration
    }

    @Test
    void testLoginFailure() {
        // Test de connexion avec des identifiants incorrects
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Remplir le champ username
        WebElement usernameField = driver.findElement(By.id("username"));
        usernameField.sendKeys("wronguser@gmail.com");

        // Remplir le champ password
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("wrongpassword");

        // Soumettre le formulaire
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
        loginButton.click();

        // Attendre et vérifier la présence du message d'erreur
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[style='color: red;'] p")));
        assertTrue(errorMessage.getText().contains("Invalid username or password."));  // Vérifie le message d'erreur
    }
}
