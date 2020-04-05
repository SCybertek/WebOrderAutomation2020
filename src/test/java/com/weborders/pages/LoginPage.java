package com.weborders.pages;

import com.weborders.utilities.ConfigurationReader;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends AbstractBasePage {

    @FindBy(id = "ctl00_MainContent_username")
    private WebElement userName;

    @FindBy(id = "ctl00_MainContent_password")
    private WebElement password;


//public AbstractBasePage(){
//
//        PageFactory.initElements(driver, this);
//    }

    //initialization is required to use FindBy annotations
    //this constructor of super class is called automatically!

    public void login() {

        String usernameValue = ConfigurationReader.getProperty("username");
        String passwordValue = ConfigurationReader.getProperty("password");

        userName.sendKeys(usernameValue);
        password.sendKeys(passwordValue, Keys.ENTER);

    }

    /**
     * Overloaded method : allows to specify different credentials instead of what we have in our config. file
     *
     * @param usernameValue
     * @param passwordValue
     */
    public void login(String usernameValue, String passwordValue) {

        userName.sendKeys(usernameValue);
        password.sendKeys(passwordValue, Keys.ENTER);

    }
    //breaking POM:
    //putting webElements outside of your Page classes
    //or storing webElement for login for example inside unrelated Page class

}
