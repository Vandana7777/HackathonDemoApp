package com.pom;

import junit.framework.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(how = How.TAG_NAME, using = "h4")
    private WebElement loginLbl;

    @FindBy(how = How.XPATH, using=".//*[contains(@id,'username')]/../label")
    private WebElement unameLbl;

    @FindBy(how = How.XPATH, using = ".//*[contains(@id,'password')]/../label")
    private WebElement pwordLbl;

    @FindBy(how = How.XPATH, using=".//label[contains(text(),'Remember Me')]")
    private WebElement rememberMeLbl;

    @FindBy(how = How.XPATH, using = ".//label[contains(text(),'Remember Me')]/input")
    private WebElement rememberMeChkBox;

    @FindBy(how = How.XPATH, using = ".//img[contains(@src,'twitter.png')]")
    private WebElement twitterLogo;

    @FindBy(how = How.XPATH, using = ".//img[contains(@src,'facebook.png')]")
    private WebElement facebookLogo;

    @FindBy(how = How.XPATH, using = ".//img[contains(@src,'linkedin.png')]")
    private WebElement linkedinLogo;

    @FindBy(how = How.ID, using = "username")
    private WebElement usernameTxtField;

    @FindBy(how = How.ID, using = "password")
    private WebElement passwordTxtField;

    @FindBy(how = How.ID, using = "log-in")
    private WebElement loginBotton;

    @FindBy(how = How.XPATH, using = ".//div[contains(@id,'random_id')]")
    private WebElement errorMessage;

    @FindBy(how = How.ID, using = "logged-user-name-new")
    private WebElement loginUserName;

    @FindBy(how = How.XPATH, using = ".//*[contains(@class,'os-icon-user-male-circle')]")
    private WebElement userIcon;

    @FindBy(how = How.XPATH, using = ".//*[contains(@class,'os-icon-fingerprint')]")
    private WebElement fingerIcon;

    String username;
    String password;

    public LoginPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 2);
    }

    public void validateLoginPage() {
        boolean flag = true;
        String errors="";

        try{
            loginLbl.isDisplayed();
            if(!loginLbl.getText().equals("Login Form")) {
                flag = false;
                System.out.println("BUG: Login form label is not as expected");
                errors=errors + "BUG: Login form label is not as expected";
            }
        } catch(Exception ex){
            flag = false;
            System.out.println("BUG: Login Form label is not displayed");
            errors=errors + "\nBUG: Login Form label is not displayed";
        }

        try{
            unameLbl.isDisplayed();
            if(!unameLbl.getText().equals("Username")) {
                flag = false;
                System.out.println("BUG: Username label is not as expected");
                errors=errors + "\nBUG: Username label is not as expected";
            }
        } catch(Exception ex){
            flag = false;
            System.out.println("BUG: Username label is not displayed");
            errors = errors + "\nBUG: Username label is not displayed";
        }

        try{
            pwordLbl.isDisplayed();
            if(!pwordLbl.getText().equals("Password")) {
                flag = false;
                System.out.println("BUG: Password label is not as expected");
                errors = errors + "\nBUG: Password label is not as expected";
            }
        } catch(Exception ex){
            flag = false;
            System.out.println("BUG: Password label is not displayed");
            errors = errors + "\nBUG: Password label is not displayed";
        }

        try{
            usernameTxtField.isDisplayed();
        } catch(Exception ex){
            flag = false;
            System.out.println("BUG: Username text field is not displayed");
            errors = errors + "\nBUG: Username text field is not displayed";
        }

        try{
            passwordTxtField.isDisplayed();
        } catch(Exception ex){
            flag = false;
            System.out.println("BUG: Password text field is not displayed");
            errors = errors + "\nBUG: Password text field is not displayed";
        }

        try{
            loginBotton.isDisplayed();
        } catch(Exception ex){
            flag = false;
            System.out.println("BUG: Login button is not displayed");
            errors = errors + "\nBUG: Login button is not displayed";
        }

        try{
            rememberMeLbl.isDisplayed();
        } catch(Exception ex){
            flag = false;
            System.out.println("BUG: Remember Me label is not displayed");
            errors = errors + "\nBUG: Remember Me label is not displayed";
        }

        try{
            rememberMeChkBox.isDisplayed();
        } catch(Exception ex){
            flag = false;
            System.out.println("BUG: Remember Me checkbox is not displayed");
            errors = errors + "\nBUG: Remember Me checkbox is not displayed";
        }

        try{
            twitterLogo.isDisplayed();
        } catch(Exception ex){
            flag = false;
            System.out.println("BUG: Twitter logo is not displayed");
            errors = errors + "\nBUG: Twitter logo is not displayed";
        }

        try{
            facebookLogo.isDisplayed();
        } catch(Exception ex){
            flag = false;
            System.out.println("BUG: Facebook logo is not displayed");
            errors = errors + "\nBUG: Facenook logo is not displayed";
        }

        try{
            linkedinLogo.isDisplayed();
        } catch(Exception ex){
            flag = false;
            System.out.println("BUG: LinkedIn logo is not displayed");
            errors = errors + "\nBUG: LinkedIn logo is not displayed";
        }

        try{
            userIcon.isDisplayed();
        } catch(Exception ex){
            flag = false;
            System.out.println("BUG: User ICON is not displayed");
            errors = errors + "\nBUG: User ICON is not displayed";
        }

        try{
            fingerIcon.isDisplayed();
        } catch(Exception ex){
            flag = false;
            System.out.println("BUG: Finger Print ICON is not displayed");
            errors = errors + "\nBUG: Finger Print ICON is not displayed";
        }

       Assert.assertTrue(errors, flag);
    }

    public void submitLogin(String username, String password) {
        this.username = username;
        this.password = password;
        usernameTxtField.clear();
        usernameTxtField.sendKeys(username);
        passwordTxtField.clear();
        passwordTxtField.sendKeys(password);
        loginBotton.click();
        try{
            wait.until(ExpectedConditions.visibilityOf(errorMessage));
        } catch(Exception ex) {

        }
    }

    public void validateLoginSuccessOrError() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(username.length()==0 && password.length()==0) {
            Assert.assertTrue("BUG: Error message is not shown on UI even though user has not entered both username and password.", errorMessage.isDisplayed());
            Assert.assertEquals("BUG: Error message is not as expected when user does not enter username and password both", "Please enter both username and password", errorMessage.getText());
        } else if(username.length()!=0 && password.length()==0) {
            Assert.assertTrue("BUG: Error message is not shown on UI even though user has not entered password.", errorMessage.isDisplayed());
            Assert.assertEquals("BUG: Error message is not as expected when user does not enter password ", "Password must be present", errorMessage.getText());
            Assert.assertTrue("BUG: Error message is not shown on UI even though user has not entered password.", !errorMessage.getAttribute("style").contains("z-index: -1"));
        } else if(username.length()==0 && password.length()!=0) {
            Assert.assertTrue("BUG: Error message is not shown on UI even though user has not entered username.", errorMessage.isDisplayed());
            Assert.assertEquals("BUG: Error message is not as expected when user does not enter username ", "Username must be present", errorMessage.getText());
        } else {
            Assert.assertTrue("BUG: User is not able to submitLogin with valid username and passsword", loginUserName.isDisplayed());
        }
    }
}
