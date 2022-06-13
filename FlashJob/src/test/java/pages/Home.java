package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;

public class Home {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(how = How.XPATH,using = "//app-root/app-header//nav[@class='navbar navbar-expand-lg p-2']//ul//a[@href='/FlashJobFront/register']")
    WebElement register;

    @FindBy(how = How.XPATH,using = "//app-root/app-header//nav[@class='navbar navbar-expand-lg p-2']//ul//a[@href='/FlashJobFront/login']")
    WebElement logIn;

    public Home(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        WebDriverWait wait = new WebDriverWait(driver,10);
    }

    public void register() {
        register.click();
    }

    public void logIn(){
        logIn.click();
    }

    //public void newPage() throws InterruptedException {
    //String parentWindow = driver.getWindowHandle();
    //Set<String> handles =  driver.getWindowHandles();
    //for(String windowHandle  : handles){
    //if(!windowHandle.equals(parentWindow)){
    //driver.switchTo().window(windowHandle);
                //Thread.sleep(5000);
                //driver.close(); //closing child window
                //driver.switchTo().window(parentWindow); //cntrl to parent window
    //}
    //}
    //}

    //Metodo generico para dar clic a un elemento
    //public void clickOnElement(WebElement element){
    //    element.click();
    //}

    //public boolean isPageOpened(String title){
    //    return gradoss.getText().contains(title);
    //}

}
