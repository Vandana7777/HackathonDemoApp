package com.test;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Eyes;
import com.pom.HomePage;
import com.pom.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public class VisualAITests {

    private static BatchInfo batch = null;
    private String V1 = "https://demo.applitools.com/hackathon.html";
    private String V2 = "https://demo.applitools.com/hackathonV2.html";
    private String dynamicContentV1 = "https://demo.applitools.com/hackathon.html?showAd=true";
    private String dynamicContentV2 = "https://demo.applitools.com/hackathonV2.html?showAd=true";

    private WebDriver driver = null;
    EyesRunner runner;
    Eyes eyes;
    private static int count = 0;

    @BeforeClass
    public static void setUpBatch() {
        batch = new BatchInfo("Hackathon");
    }

    @DataProvider(name = "userdetails")
    public Object[][] dataProvider() {
        return new Object[][] { { "","" }, { "username","" }, {"", "password"}, {"username", "password"} };
    }

    @BeforeTest
    public void initiate() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//src//main//resources//driverEXEs//chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        runner = new ClassicRunner();
        eyes = new Eyes(runner);
        eyes.setApiKey("BuGXsxkNyYqVFCBJ6m2QeadKHrorZPOv0sHinTF03VU110");
        eyes.setForceFullPageScreenshot(true);
    }

    @Test
    public void LoginPageUIElementTest() {
        eyes.setBatch(batch);
        eyes.open(driver, "ApplitoolApp", "Login Page UI Element Test");
        driver.get(V2);
        eyes.checkWindow("Login UI Page");
        eyes.closeAsync();
        eyes.abortIfNotClosed();
    }

    @Test(dataProvider = "userdetails")
    public void LoginFeatureTest(String username, String password) {
        if(count==0){
            eyes.setBatch(batch);
        }
        count++;
        eyes.open(driver, "ApplitoolApp", "Data Driver Test "+count);
        driver.get(V2);
        LoginPage loginPageObj = new LoginPage(driver);
        loginPageObj.submitLogin(username, password);
        if(username.length()!=0 && password.length()!=0)
            eyes.checkWindow("Home Page "+count);
        else
            eyes.checkWindow("Login Page error "+count);
        eyes.closeAsync();
        eyes.abortIfNotClosed();
    }

    @Test
    public void TableSortTest() {
        eyes.setBatch(batch);
        eyes.open(driver, "ApplitoolApp", "Recent Transaction Table Sort Test");
        driver.get(V2);
        LoginPage loginPageObj = new LoginPage(driver);
        loginPageObj.submitLogin("username", "password");

        HomePage homePageObj = new HomePage(driver);
        homePageObj.clickAmountHeader();
        eyes.checkWindow("Recent Transactions");
        eyes.closeAsync();
        eyes.abortIfNotClosed();
    }

    @Test
    public void CanvasChartTest() {
        eyes.setBatch(batch);
        eyes.open(driver, "ApplitoolApp", "Compare Expenses - Chart Test");
        driver.get(V2);
        LoginPage loginPageObj = new LoginPage(driver);
        loginPageObj.submitLogin("username", "password");

        HomePage homePageObj = new HomePage(driver);
        homePageObj.clickCompareExpensesLink();
        eyes.checkWindow("Compare Expenses");
        homePageObj.clickShowNextYearDataBtn();
        eyes.checkWindow("Compare Expenses - Show Next Year Data");
        eyes.closeAsync();
        eyes.abortIfNotClosed();
    }

    @Test
    public void DynamicContentTest() {
        eyes.setBatch(batch);
        eyes.open(driver, "ApplitoolApp", "Flash GIF Test");
        driver.get(dynamicContentV2);
        LoginPage loginPageObj = new LoginPage(driver);
        loginPageObj.submitLogin("username", "password");
        eyes.checkWindow("Home Page - Flash sale");
        eyes.closeAsync();
        eyes.abortIfNotClosed();
    }

    @AfterTest
    public void closeBrowser() {
        eyes = null;
        runner = null;
        if(driver != null) {
            driver.quit();
        }
    }

}
