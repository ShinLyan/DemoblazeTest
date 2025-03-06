import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTest {
    private WebDriver driver;
    private final String BASE_URL = "https://www.demoblaze.com/";

    private final String CORRECT_LOGIN = "123";

    private final String CORRECT_PASSWORD = "123";

    // Тест-кейс: Успешный вход в систему
    // 1. Открыть сайт Demoblaze
    // 2. Нажать кнопку "Log in"
    // 3. Ввести корректные учетные данные
    // 4. Нажать кнопку "Log in"
    // 5. Проверить, что пользователь успешно вошел
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
    public void testSuccessfulLogin() {
        performLogin(CORRECT_LOGIN, CORRECT_PASSWORD);
        validateWelcomeMessage(CORRECT_LOGIN);
    }

    private void performLogin(String username, String password) {
        driver.findElement(By.id("login2")).click();
        driver.findElement(By.id("loginusername")).sendKeys(username);
        driver.findElement(By.id("loginpassword")).sendKeys(password);
        driver.findElement(By.xpath("//button[contains(text(),'Log in')]")).click();
    }

    private void validateWelcomeMessage(String username) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement welcomeMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nameofuser")));

        String expectedMessage = "Welcome " + username;
        Assert.assertEquals(welcomeMessage.getText(), expectedMessage, "Текст приветствия некорректен");
    }
}