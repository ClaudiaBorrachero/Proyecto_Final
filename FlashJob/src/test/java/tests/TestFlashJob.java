package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.Home;
import pages.Login;
import pages.Register;

public class TestFlashJob {

    private String PATHDRIVER = "src/test/resources/drivers/";
    private String baseURL = "https://claudiaborrachero.github.io/FlashJobFront";
    private WebDriver driver;
    private JavascriptExecutor js;
    //private WebDriverWait wait;

    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver",PATHDRIVER+"chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("--incognito");
        driver = new ChromeDriver(options);
        js = (JavascriptExecutor) driver;
        //wait = new WebDriverWait(driver,10);
    }

    @Test
    public void flashjob() throws InterruptedException {
        driver.get(baseURL);
        Home home = new Home(driver);
        Register register = new Register(driver);
        Login login = new Login(driver);
        //js.executeScript("window.scrollBy(0,3300)");

        home.register();
        register.register();

        driver.get(baseURL);
        Thread.sleep(2000);
        home.logIn();
        Thread.sleep(2000);
        login.logIn();

        if(!driver.getCurrentUrl().contains("https://claudiaborrachero.github.io/FlashJobFront")) {
            throw new RuntimeException("Error en la URL");
        }


    }

    @After
    public void close() throws InterruptedException {
        Thread.sleep(2000);
        //driver.close();
        driver.quit();
    }

}
