import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginInvalidTest {
    private WebDriver driver;
    private final String BASE_URL = "https://www.demoblaze.com/";

    // Тест-кейс: Вход с некорректными данными
    // 1. Открыть сайт Demoblaze
    // 2. Нажать кнопку "Log in"
    // 3. Ввести некорректные учетные данные
    // 4. Нажать кнопку "Log in"
    // 5. Проверить, что появилось сообщение об ошибке
    @BeforeMethod
    public void setup() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(BASE_URL);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testLoginWithInvalidCredentials() {
        driver.findElement(By.id("login2")).click();
        driver.findElement(By.id("loginusername")).sendKeys("invalidUser");
        driver.findElement(By.id("loginpassword")).sendKeys("wrongPassword");
        driver.findElement(By.xpath("//button[contains(text(),'Log in')]")).click();

        // Ожидаем появления alert
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.alertIsPresent());

        // Переключаемся на alert
        Alert alert = driver.switchTo().alert();

        // Проверяем текст в alert
        String alertText = alert.getText();
        Assert.assertEquals(alertText, "Wrong password.", "Текст ошибки не совпадает!");

        // Закрываем alert
        alert.accept();
    }
}