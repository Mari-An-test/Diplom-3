import config.Config;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import page.MainPage;

import static org.junit.Assert.assertTrue;

public class ConstructorTest {
    public WebDriver driver;
    public MainPage mainPage;

    @Before
    public void setUp() {
        driver = WebDriverEn.setUpDriver();
        mainPage = new MainPage(driver);
        driver.get(Config.SITE_ADDRESS);
    }

    @Test
    public void checkSaucesPass() {
        mainPage.clickSauces();
        assertTrue("Выбрана вкладка соусов", mainPage.saucesSelected());
    }

    @Test
    public void checkFillingsPass() {
        mainPage.clickFillings();
        assertTrue("Выбрана вкладка начинок", mainPage.fillingsSelected());
    }

    @Test
    public void checkBunsPass() {
        mainPage.clickFillings();
        mainPage.clickBuns();
        assertTrue("Выбрана вкладка соусов", mainPage.bunsSelected());
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}