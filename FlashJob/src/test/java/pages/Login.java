package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.logging.Logger;

public class Login {

    private WebDriver driver;
    private WebDriverWait wait;
    private Logger log;

    @FindBy(how = How.XPATH,using = "/html//input[@id='email']")
    WebElement email;

    @FindBy(how = How.XPATH,using = "/html//input[@id='password']")
    WebElement passw;

    @FindBy(how = How.XPATH,using = "//app-root/div[@class='container-fluid']/div[@class='pb-5']/app-login/section//form/button[@type='submit']")
    WebElement login;

    public Login(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        WebDriverWait wait = new WebDriverWait(driver,10);
    }

    public void logIn() throws InterruptedException {
        email.sendKeys("javirh@gmail.com");
        Thread.sleep(2000);
        passw.sendKeys("123456");
        Thread.sleep(3000);
        login.click();
    }

}
