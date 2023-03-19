import config.Config;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import model.User;
import model.UserRequest;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import page.LoginPage;
import page.MainPage;
import page.RegisterPage;
import page.PasswordPage;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class LoginTest {

    public static String accessToken;
    public static User user;
    public WebDriver driver;
    public LoginPage loginPage;
    public MainPage mainPage;

    @BeforeClass
    public static void beforeClass() {
        RestAssured.baseURI = Config.SITE_ADDRESS;
        user = User.createRandomUser();
        accessToken = UserRequest.createUser(user).path("accessToken");
    }

    @AfterClass
    public static void afterClass() {
        UserRequest.deleteUser(accessToken);
    }

    @Before
    public void setUp() {
        driver = WebDriverEn.setUpDriver();
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
    }

    @Test
    public void checkLoginFromLoginPage() {
        driver.get(Config.SITE_ADDRESS + Config.LOGIN_URL);
        checks();
    }

    @Test
    public void checkLoginFromMainPagePersonal() {
        driver.get(Config.SITE_ADDRESS);
        mainPage.waitLoading();
        mainPage.clickPersonalText();
        checks();
    }

    @Test
    public void checkLoginFromMainPageEnterAccount() {
        driver.get(Config.SITE_ADDRESS);
        mainPage.waitLoading();
        mainPage.clickEnterAccount();
        checks();
    }

    @Test
    public void checkLoginFromRegistrationHyperlink() {
        driver.get(Config.SITE_ADDRESS + Config.REGISTER_URL);
        RegisterPage registrationPage = new RegisterPage(driver);
        registrationPage.clickSingInHyperlink();
        checks();
    }

    @Test
    public void checkLoginFromForgotPasswordHyperlink() {
        driver.get(Config.SITE_ADDRESS + Config.FORGOT_URL);
        PasswordPage restorePasswordPage = new PasswordPage(driver);
        restorePasswordPage.clickSingInHyperlink();
        checks();
    }

    public void checks() {
        assertTrue("Зашли на страницу логина", pageURLIsLoginPage());
        loginPage.enterCredsAndClickLogin(user);
        assertTrue(loadedPageAfterSuccessfulLogin());
    }

    @Step("We are at Login page")
    public boolean pageURLIsLoginPage() {
        loginPage.waitLoading();
        return driver.getCurrentUrl().equals(Config.SITE_ADDRESS + Config.LOGIN_URL);
    }

    @Step("After login, we have to be redirected to main page and button Create Order is present")
    public boolean loadedPageAfterSuccessfulLogin() {
        mainPage.waitLoading();
        return mainPage.createOrderDispalyed();
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}