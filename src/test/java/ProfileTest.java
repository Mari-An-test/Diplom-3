import config.Config;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.User;
import model.UserRequest;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import page.LoginPage;
import page.MainPage;
import page.ProfilePage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ProfileTest {

    public static String accessToken, refreshToken;
    public static User user;
    public WebDriver driver;
    public MainPage mainPage;
    public LoginPage loginPage;
    public ProfilePage profilePage;

    @BeforeClass
    public static void beforeClass() {
        RestAssured.baseURI = Config.SITE_ADDRESS;
        user = User.createRandomUser();
        Response response = UserRequest.createUser(user);
        accessToken = response.path("accessToken");
        refreshToken = response.path("refreshToken");
    }

    @AfterClass
    public static void afterClass() {
        UserRequest.deleteUser(accessToken);
    }

    @Before
    public void setUp() {
        driver = WebDriverEn.setUpDriver();
        mainPage = new MainPage(driver);
        profilePage = new ProfilePage(driver);
        loginPage = new LoginPage(driver);
        driver.get(Config.SITE_ADDRESS);
        LocalStorage localStorage = ((WebStorage) driver).getLocalStorage();
        localStorage.setItem("accessToken", accessToken);
        localStorage.setItem("refreshToken", refreshToken);
    }

    @Test
    public void checkProfileExitButton() {
        driver.get(Config.SITE_ADDRESS + "/account");
        profilePage.waitLoading();
        profilePage.clickExitButton();
        loginPage.waitLoading();
        LocalStorage localStorage = ((WebStorage) driver).getLocalStorage();
        assertNull("Access-токен очистился", localStorage.getItem("accessToken"));
        assertEquals("Оказались на странице логина после выхода", Config.SITE_ADDRESS + Config.LOGIN_URL, driver.getCurrentUrl());
    }


    @Test
    public void checkFromProfileToMainViaLogoPassage() {
        driver.get(Config.SITE_ADDRESS + "/account");
        profilePage.waitLoading();
        profilePage.clickLogo();
        mainPage.waitLoading();
        assertEquals("Оказались на главной странице после Лого", Config.SITE_ADDRESS + "/", driver.getCurrentUrl());
    }

    @Test
    public void checkFromProfileToMainViaConstructorPassage() {
        driver.get(Config.SITE_ADDRESS + "/account");
        profilePage.waitLoading();
        profilePage.clickConstructor();
        mainPage.waitLoading();
        assertEquals("Оказались на главной странице после Конструктора", Config.SITE_ADDRESS + "/", driver.getCurrentUrl());
    }

    @Test
    public void checkFromMainToProfilePassage() {
        mainPage.waitLoading();
        mainPage.clickPersonalText();
        profilePage.waitLoading();
        assertEquals("Оказались в личном кабинете после главной страницы", Config.SITE_ADDRESS + Config.PERSONAL, driver.getCurrentUrl());
    }

    @After
    public void tearDown() {
        System.out.println(driver.manage().getCookies());
        driver.quit();
    }
}