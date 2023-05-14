import config.Config;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.User;
import model.UserRequest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import page.LoginPage;
import page.RegisterPage;

public class RegisterTest {

    public WebDriver driver;

    public RegisterPage registerPage;
    public String accessToken;
    public LoginPage loginPage;

    @Before
    public void setUp() {
        RestAssured.baseURI = Config.SITE_ADDRESS;
        driver = WebDriverEn.setUpDriver();
        registerPage = new RegisterPage(driver);
        driver.get(Config.SITE_ADDRESS + Config.REGISTER_URL);
        registerPage.waitLoading();
    }

    @Test
    public void registerTest() {
        User user = User.createRandomUser();
        loginPage = new LoginPage(driver);
        registerPage.enterRegisterUserFields(user);
        registerPage.clickFinallyRegisterButton();
        loginPage.waitLoading();
        Assert.assertEquals("Перешли на страницу логина", Config.SITE_ADDRESS + Config.LOGIN_URL, driver.getCurrentUrl());
        Response response = UserRequest.authUser(user);
        Assert.assertEquals("Удалось залогиниться с данными созданного пользователя", 200, response.statusCode());
        accessToken = response.path("accessToken");
    }

    @Test
    public void shortPasswordTest() {
        User user = User.createRandomUser();
        user.setPassword("12345");
        registerPage.enterRegisterUserFields(user);
        registerPage.clickFinallyRegisterButton();
        Assert.assertTrue("Отображается ошибка о некорректном пароле", registerPage.checkShortPasswordError());
        Assert.assertEquals("Остались на странице логина", Config.SITE_ADDRESS + Config.REGISTER_URL, driver.getCurrentUrl());
        Response response = UserRequest.authUser(user);
        Assert.assertFalse("Не удалось залогиниться с данными созданного пользователя", response.path("success"));
    }

    @After
    public void tearDown() {
        driver.quit();
        if (accessToken != null) {
            UserRequest.deleteUser(accessToken);
        }

    }
}