package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.logging.Logger;

public class Register {

    private WebDriver driver;
    private WebDriverWait wait;
    private Logger log;

    @FindBy(how = How.XPATH,using = "//app-root/div[@class='container-fluid']/div[@class='pb-5']/app-register/section/div/div/div/div//form/div[1]/input[@type='text']")
    WebElement firstName;

    @FindBy(how = How.XPATH,using = "//app-root/div[@class='container-fluid']/div[@class='pb-5']/app-register/section/div/div/div/div//form/div[2]/input[@type='text']")
    WebElement lastName;

    @FindBy(how = How.XPATH,using = "//app-root/div[@class='container-fluid']/div[@class='pb-5']/app-register/section/div/div/div/div//form//input[@type='email']")
    WebElement email;

    @FindBy(how = How.XPATH,using = "//app-root/div[@class='container-fluid']/div[@class='pb-5']/app-register/section/div/div/div/div//form/div[4]/input[@type='text']")
    WebElement phoneNumber;

    @FindBy(how = How.XPATH,using = "//app-root/div[@class='container-fluid']/div[@class='pb-5']/app-register/section/div/div/div/div//form/div[5]/input[@type='password']")
    WebElement passw1;

    @FindBy(how = How.XPATH,using = "//app-root/div[@class='container-fluid']/div[@class='pb-5']/app-register/section/div/div/div/div//form/div[6]/input[@type='password']")
    WebElement passw2;

    @FindBy(how = How.XPATH,using = "//app-root/div[@class='container-fluid']/div[@class='pb-5']/app-register/section/div/div/div/div//form/div[@class='col-md-12 text-start']/input[@type='text']")
    WebElement location;

    @FindBy(how = How.XPATH,using = "//app-root/div[@class='container-fluid']/div[@class='pb-5']/app-register/section/div/div/div/div//form")
    WebElement register;

    public Register(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        WebDriverWait wait = new WebDriverWait(driver,10);
    }

    public void register() throws InterruptedException {
        firstName.sendKeys("Javier");
        Thread.sleep(1000);
        lastName.sendKeys("Ruiz Hidalgo");
        Thread.sleep(1000);
        email.sendKeys("javirh@gmail.com");
        Thread.sleep(1000);
        phoneNumber.sendKeys("608976783");
        Thread.sleep(1000);
        passw1.sendKeys("123456");
        Thread.sleep(1000);
        passw2.sendKeys("123456");
        Thread.sleep(1000);
        location.sendKeys("Sevilla");
        Thread.sleep(3000);
        register.click();
    }
}
