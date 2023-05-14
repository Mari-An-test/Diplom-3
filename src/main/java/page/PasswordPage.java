package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PasswordPage {
    private final By signInHyperlink = By.xpath(".//a[text()='Войти']");
    private final WebDriver driver;

    public PasswordPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickSingInHyperlink() {
        driver.findElement(signInHyperlink).click();
    }
}