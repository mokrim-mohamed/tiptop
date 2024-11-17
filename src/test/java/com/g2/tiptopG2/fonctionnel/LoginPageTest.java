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
        driver.get("https://34.1.9.181:8080/");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void testLoginSuccess() {
        // Attendre que la page se charge
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Vérifier que la page contient le texte "Bienvenue"
        WebElement welcomeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h2")));
        assertTrue(welcomeElement.getText().contains("Bienvenue"));

        // Remplir le champ username
        WebElement usernameField = driver.findElement(By.id("username"));
        usernameField.sendKeys("admin@gmail.com");

        // Remplir le champ password
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("mokrim1234");

        // Soumettre le formulaire
        WebElement loginButton = driver.findElement(By.tagName("button"));
        loginButton.click();

        // Vérifier la redirection ou la présence d'un message d'erreur
        wait.until(ExpectedConditions.urlContains("admin/dashboard")); // Vérifie si la redirection a eu lieu
    }

    @Test
    void testLoginFailure() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Remplir le champ username avec un mauvais identifiant
        WebElement usernameField = driver.findElement(By.id("username"));
        usernameField.sendKeys("wronguser");

        // Remplir le champ password avec un mauvais mot de passe
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("wrongpassword");

        // Soumettre le formulaire
        WebElement loginButton = driver.findElement(By.tagName("button"));
        loginButton.click();

        // Vérifier si un message d'erreur est affiché
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@style='color: red;']")));
        assertTrue(errorMessage.getText().contains("Invalid username or password"));
    }
}
