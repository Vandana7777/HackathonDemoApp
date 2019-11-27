package com.pom;

import junit.framework.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;

public class HomePage {

    private WebDriverWait wait;
    private WebDriver driver;

    @FindBy(how = How.ID, using = "logged-user-name-new")
    private WebElement userName;

    @FindBy(how = How.ID, using = "amount")
    private WebElement amountHeader;

    @FindBy(how = How.LINK_TEXT, using = "Compare Expenses")
    private WebElement compareExpensesLink;

    @FindBy(how = How.ID, using = "addDataset")
    private WebElement showNextYearExpenseBtn;

    @FindBy(how = How.XPATH, using=".//*[@id='flashSale']/img[contains(@src,'.gif')]")
    private WebElement gif1;

    @FindBy(how = How.XPATH, using=".//*[@id='flashSale2']/img[contains(@src,'.gif')]")
    private WebElement gif2;

    private List<Map<String, String>> recentTransactionList;
    private List<Map<String, String>> ascendingTransactionList;
    private Set<Double> amountSet = new TreeSet<>();
    private List<Double> ascendingAmountList = new ArrayList<>();

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        recentTransactionList = new ArrayList<>();
        wait = new WebDriverWait(driver, 2);
    }

    public void fetchRecentTransactions() {
        wait.until(ExpectedConditions.visibilityOf(userName));
        ArrayList<String> headerList = new ArrayList<>();
        List<WebElement> headerElements = driver.findElements(By.tagName("th"));
        for(WebElement header: headerElements){
            headerList.add(header.getText());
        }
        List<WebElement> rowList = driver.findElements(By.xpath(".//tbody/tr"));
        Map<String, String>  map;
        int count;
        for(WebElement row: rowList) {
            List<WebElement> colList = row.findElements(By.tagName("td"));
            map = new HashMap<>();
            count = 0;
            for(WebElement col: colList) {
                List<WebElement> elementList;
                if(headerList.get(count).equalsIgnoreCase("category"))
                    elementList = col.findElements(By.tagName("a"));
                else
                    elementList = col.findElements(By.tagName("span"));
                if(headerList.get(count).equalsIgnoreCase("amount"))
                    amountSet.add(convertStringtoNumber(elementList.get(elementList.size()-1).getText()));
                map.put(headerList.get(count), elementList.get(elementList.size()-1).getText());
                count++;
            }
            recentTransactionList.add(map);
        }
    }

    public double convertStringtoNumber(String amountStr) {
        int len = amountStr.length();
        int flag = 1;
        String subStr = amountStr.substring(2, len-4);
        double amount = Double.valueOf(subStr.replace(",",""));
        if(amountStr.contains("-"))
            flag = -1;
        return amount*flag;
    }

    public void clickAmountHeader() {
        wait.until(ExpectedConditions.visibilityOf(amountHeader));
        amountHeader.click();
    }

    public void verifyRecentTransactions() {
        getRecentTransactions();
        Assert.assertTrue("BUG: Amounts are not shown in ascending order after clicking on Amount header", compareAmountInAscendingOrder());
        comopareRecentTransactionsAfterSorting();
    }

    public boolean compareAmountInAscendingOrder() {
        if(amountSet.size() != ascendingAmountList.size())
            return false;
        else {
            int count = 0;
            for(Double amount: amountSet) {
                if(!amount.equals(ascendingAmountList.get(count))) {
                    return false;
                }
                count++;
            }
        }

        return true;
    }

    public void comopareRecentTransactionsAfterSorting() {
        boolean flag = false;
        for(Map<String, String> map1 : ascendingTransactionList) {
            flag = false;
            for(Map<String, String> map2: recentTransactionList) {
                if(map1.get("AMOUNT").equalsIgnoreCase(map2.get("AMOUNT"))) {
                    flag = true;
                    Assert.assertTrue("BUG: Each row's data is not in tact after clicking on Amount header", map1.equals(map2));
                    break;
                }
            }
        }
    }

    public void getRecentTransactions() {
        ascendingTransactionList = new ArrayList<>();
        wait.until(ExpectedConditions.visibilityOf(userName));
        ArrayList<String> headerList = new ArrayList<>();
        List<WebElement> headerElements = driver.findElements(By.tagName("th"));
        for(WebElement header: headerElements){
            headerList.add(header.getText());
        }
        List<WebElement> rowList = driver.findElements(By.xpath(".//tbody/tr"));
        List<Map<String, String>> recentTransactions = new ArrayList<>();
        Map<String, String>  map;
        int count;
        for(WebElement row: rowList) {
            List<WebElement> colList = row.findElements(By.tagName("td"));
            map = new HashMap<>();
            count = 0;
            for(WebElement col: colList) {
                List<WebElement> elementList;
                if(headerList.get(count).equalsIgnoreCase("category"))
                    elementList = col.findElements(By.tagName("a"));
                else
                    elementList = col.findElements(By.tagName("span"));
                if(headerList.get(count).equalsIgnoreCase("amount"))
                    ascendingAmountList.add(convertStringtoNumber(elementList.get(elementList.size()-1).getText()));
                map.put(headerList.get(count), elementList.get(elementList.size()-1).getText());
                count++;
            }
            ascendingTransactionList.add(map);
        }
    }

    public void verifyPresenceOfFlashGIF1() {
        try {
            gif1.isDisplayed();
        }catch(Exception ex){
            Assert.assertTrue("BUG: Flash sale GIF 1 is not displayed", false);
        }
    }

    public void verifyPresenceOfFlashGIF2() {
        try {
            gif2.isDisplayed();
        }catch(Exception ex){
            Assert.assertTrue("BUG: Flash sale GIF 2 is not displayed", false);
        }
    }

    public void clickCompareExpensesLink() {
        wait.until(ExpectedConditions.visibilityOf(compareExpensesLink));
        compareExpensesLink.click();
        wait.until(ExpectedConditions.visibilityOf(showNextYearExpenseBtn));
    }

    public void clickShowNextYearDataBtn() {
        wait.until(ExpectedConditions.visibilityOf(showNextYearExpenseBtn));
        showNextYearExpenseBtn.click();
        try {
            Thread.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
