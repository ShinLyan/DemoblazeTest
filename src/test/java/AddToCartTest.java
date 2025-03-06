import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Alert;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class AddToCartTest {
    private WebDriver driver;
    private final String BASE_URL = "https://www.demoblaze.com/";

    // Тест-кейс: Добавление товара в корзину
    // 1. Открыть сайт Demoblaze
    // 2. Выбрать товар (Samsung Galaxy S6)
    // 3. Нажать "Add to cart"
    // 4. Ожидать появления модального окна
    // 5. Проверить, что текст модального окна "Product added"
    // 6. Подтвердить модальное окно
    // 7. Перейти в корзину и проверить наличие товара
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
    public void testAddProductToCart() {
        driver.findElement(By.xpath("//a[contains(text(),'Samsung galaxy s6')]")).click();
        driver.findElement(By.xpath("//a[contains(text(),'Add to cart')]")).click();

        // Ожидание и проверка модального окна
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.alertIsPresent());

        Alert alert = driver.switchTo().alert();
        Assert.assertEquals(alert.getText(), "Product added", "Текст в модальном окне некорректен");
        alert.accept();

        // Переход в корзину
        driver.findElement(By.id("cartur")).click();

        // Проверяем, что товар добавлен
        WebElement productInCart = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[contains(text(),'Samsung galaxy s6')]")));
        Assert.assertTrue(productInCart.isDisplayed(), "Товар не добавлен в корзину");
    }
}