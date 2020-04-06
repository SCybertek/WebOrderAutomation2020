package com.weborders.tests;

import com.weborders.pages.LoginPage;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class LoginTest extends AbstractBaseTest {

    @Test
    public void login(){
        //below line is required by extendsReports. if you do not add it test will fail!
        extentTest = extentReports.createTest("Verify page logo");

        LoginPage loginPage = new LoginPage();
        loginPage.login();
        assertEquals(loginPage.getPageLogoText(), "Web Orders");

        extentTest.pass("Logo verified!");
    }
}
