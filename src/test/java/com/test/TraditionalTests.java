package com.test;

import com.pom.HomePage;
import com.pom.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class TraditionalTests {

    private String V1 = "https://demo.applitools.com/hackathon.html";
    private String V2 = "https://demo.applitools.com/hackathonV2.html";
    private String dynamicContentV1 = "https://demo.applitools.com/hackathon.html?showAd=true";
    private String dynamicContentV2 = "https://demo.applitools.com/hackathonV2.html?showAd=true";

    private WebDriver driver = null;

    @DataProvider(name = "userdetails")
    public Object[][] dataProviderMethod() {
        return new Object[][] { { "","" }, { "username","" }, {"", "password"}, {"username", "password"} };
    }

    @BeforeTest
    public void initiate() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//src//main//resources//driverEXEs//chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
    }

    @Test
    public void LoginPageUIElementTest() {
        driver.get(V2);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.validateLoginPage();
    }

    @Test(dataProvider = "userdetails")
    public void LoginFeatureTest(String username, String password) {
        driver.get(V2);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.submitLogin(username, password);
        loginPage.validateLoginSuccessOrError();
    }

    @Test
    public void TableSortTest() {
        driver.get(V2);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.submitLogin("username", "password");
        loginPage.validateLoginSuccessOrError();

        HomePage homePage = new HomePage(driver);
        homePage.fetchRecentTransactions();
        homePage.clickAmountHeader();
        homePage.verifyRecentTransactions();
    }

    @Test
    public void CanvasChartTest() {
        //As complete chart is single UI element and there is no way to interact with each bar, it is not possible to automate this scenario
    }

    @Test
    public void DynamicContentTest() {
        driver.get(dynamicContentV2);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.submitLogin("username", "password");
        loginPage.validateLoginSuccessOrError();

        HomePage homePage = new HomePage(driver);
        homePage.verifyPresenceOfFlashGIF1();
        homePage.verifyPresenceOfFlashGIF2();
    }

    @AfterTest
    public void closeBrowser() {
        if(driver != null) {
            driver.quit();
        }
    }

}
