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

public class PurchaseTest {
    private WebDriver driver;
    private final String BASE_URL = "https://www.demoblaze.com/";

    // Тест-кейс: Оформление заказа
    // 1. Добавить товар в корзину
    // 2. Открыть корзину и нажать "Place Order"
    // 3. Ввести данные для покупки
    // 4. Подтвердить заказ
    // 5. Проверить, что появилось сообщение о подтверждении покупки
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
    public void testCheckoutProcess() {
        driver.findElement(By.id("cartur")).click();
        driver.findElement(By.xpath("//button[contains(text(),'Place Order')]")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));

        nameField.sendKeys("Test User");
        driver.findElement(By.id("country")).sendKeys("USA");
        driver.findElement(By.id("city")).sendKeys("New York");
        driver.findElement(By.id("card")).sendKeys("1234567812345678");
        driver.findElement(By.id("month")).sendKeys("12");
        driver.findElement(By.id("year")).sendKeys("2025");

        driver.findElement(By.xpath("//button[contains(text(),'Purchase')]")).click();

        WebElement confirmationMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Thank you for your purchase!')]")));
        Assert.assertTrue(confirmationMessage.isDisplayed(), "Сообщение о подтверждении покупки не отображается");
    }
}