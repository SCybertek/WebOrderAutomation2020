package com.weborders.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.weborders.utilities.BrowserUtilities;
import com.weborders.utilities.ConfigurationReader;
import com.weborders.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import java.io.IOException;

public abstract class AbstractBaseTest {

    //we will not keep waits or actions in here from now on

//Yesterday I did ExtentReports as static and @Before @After methods/tests as (always = true)
// I was able to run XML with multiple classes without creating new <test> ??


    protected WebDriver driver;
    protected static ExtentReports extentReports;
    //we made it static in order to use in a class with multiple tests
    //we were running into NullPointerExceptions while running couple test at the same time
    protected static ExtentHtmlReporter extentHtmlReporter;
    protected static ExtentTest extentTest;


    @BeforeTest
    public void beforeTest(){
        extentReports = new ExtentReports(); // creating object
        //below is the path for the reports :
        String reportPath = "";
        //we need this if statement because of different operation system that has different path
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            reportPath = System.getProperty("user.dir") + "\\test-output\\report.html";
        } else {
            reportPath = System.getProperty("user.dir") + "/test-output/report.html";
        }

        //creating HTML report itself : we also specify where report will be saved => reportPath
        extentHtmlReporter = new ExtentHtmlReporter(reportPath);
        //attaching our report to the object we created earlier(extentReports = new ExtentReports();) to generate report
        extentReports.attachReporter(extentHtmlReporter);
        //info about project : we added report name as WebOrders Automation
        //besides name we can add many other info , like setting Date and time for our report
        extentHtmlReporter.config().setReportName("WebOrder Automation");
    }
    @AfterTest
    public void afterTest(){
        extentReports.flush();
    }

    @BeforeMethod
    public void setup() {
        driver = Driver.getDriver();
        driver.get(ConfigurationReader.getProperty("url"));
        driver.manage().window().maximize();

    }

    //AfterMethod will be asking for the testResult from our report
    @AfterMethod
    public void teardown(ITestResult testResult) {
        if (testResult.getStatus() == ITestResult.FAILURE) {
            String screenshotLocation = BrowserUtilities.getScreenshot(testResult.getName());
            //get the screenshot with test name
            try {
                // if something will go wring with screenshot attachment
                // it will stop and enforce the program to stop and you will NEED to fix the problem

                extentTest.fail(testResult.getName());//test name that failed
                extentTest.addScreenCaptureFromPath(screenshotLocation); //screenshot as an evidence
                extentTest.fail(testResult.getThrowable()); //error message
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(testResult.getStatus() == ITestResult.SUCCESS){
            extentTest.pass(testResult.getName());
        }else if(testResult.getStatus() == ITestResult.SKIP){
            extentTest.skip(testResult.getName());
        }
        BrowserUtilities.wait(3);
        Driver.closeDriver();
    }


}
