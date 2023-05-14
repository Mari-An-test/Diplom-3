import config.Config;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class WebDriverEn {
    public static WebDriver setUpDriver(){
        WebDriverManager.chromedriver().setup();
        if (System.getProperty("browser.type") != null && System.getProperty("browser.type").equals("yandex")) {
            System.setProperty("webdriver.chrome.driver", Config.YANDEXDRIVER_PATH);
        }
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }
}