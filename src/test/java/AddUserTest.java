/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author samithahewawasam
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class AddUserTest {

    // Config
    WebDriver driver = new FirefoxDriver(); // [ Firefox Version 46 ]
    String name = "samitha";
    String email = "samithahewawasam@gmail.com";
    String invalidEmail = "bla@foo";
    String password = "123456";
    String invalidPassword = "12345";

    @Before
    public void setup() throws Exception {

        driver.manage().window().maximize();

        // navigate to the app
        driver.get("http://85.93.17.135:9000/user/new");

    }

    @After
    public void afterTests() throws Exception {
    
        // Close the browser
        driver.quit();

    }

    @Test
    public void A() throws Exception {

        // Check the title of the New User Page
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                return d.getTitle().contains("New User");
            }
        });

        // Check header contains New User
        String header = driver.findElement(By.cssSelector("div.page-header > h1")).getText();
        assertTrue((header.contains("New User")));

        // Check required fields [ name, email, password ]
        // Submit the form without filling anything
        WebElement submitButtonFirst;
        submitButtonFirst = driver.findElement(By.cssSelector("div.form-actions > button"));
        submitButtonFirst.click();

        // wait for name,email,password is required error
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                return d.findElement(By.id("user.name.error")).getText().contains("Required")
                        && d.findElement(By.id("user.password.error")).getText().contains("Required")
                        && d.findElement(By.id("user.password.error")).getText().contains("Required");
            }
        });

        // Check invalid email address
        WebElement formInvalidEmail = driver.findElement(By.id("email"));
        formInvalidEmail.sendKeys(invalidEmail);

        // Submit the form
        WebElement submitButtonForInvalidEmail;
        submitButtonForInvalidEmail = driver.findElement(By.cssSelector("div.form-actions > button"));
        submitButtonForInvalidEmail.click();

        //Wait for Invalid email address error
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                return d.findElement(By.id("user.email.error")).getText().contains("Invalid email address");
            }
        });

        // Check for password mismatched [ password != confirmationPassword ]
        WebElement formPasswordMismatched = driver.findElement(By.id("password"));
        formPasswordMismatched.sendKeys(password);

        // Send invalid password to the confirmation password
        WebElement formInvalidPassword = driver.findElement(By.id("confirmationPassword"));
        formInvalidPassword.sendKeys(invalidPassword);

        // Submit the form
        WebElement submitButtonForInvalidPassword;
        submitButtonForInvalidPassword = driver.findElement(By.cssSelector("div.form-actions > button"));
        submitButtonForInvalidPassword.click();

        //Wait for password mismatch error
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                return d.findElement(By.id("user.confirmationPassword.error")).getText().contains("passwords are not the same");
            }
        });

    }

    @Test
    public void B() throws Exception {
        
        // Check the title of the New User Page
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                return d.getTitle().contains("New User");
            }
        });

        // Check header contains New User
        String header = driver.findElement(By.cssSelector("div.page-header > h1")).getText();
        assertTrue((header.contains("New User")));

        // Procees further if validation of the form is passed
        // Add name to the name input
        WebElement formName = driver.findElement(By.id("name"));
        formName.sendKeys(name);

        // Add email to the email input
        WebElement formEmail = driver.findElement(By.id("email"));
        formEmail.sendKeys(email);

        // Add password to the password input
        WebElement formPassword = driver.findElement(By.id("password"));
        formPassword.sendKeys(password);

        // Add confirm password to the confirm password input
        WebElement formConfirmPassword = driver.findElement(By.id("confirmationPassword"));
        formConfirmPassword.sendKeys(password);

        // Submit the form
        WebElement submitButton = driver.findElement(By.cssSelector("div.form-actions > button"));
        submitButton.click();

        //Check User Navigate to the All Users Page
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                return d.getTitle().contains("All User");
            }
        });

        // Check header contains All User
        String headerAllUser = driver.findElement(By.cssSelector("div.page-header > h1")).getText();
        assertTrue((headerAllUser.contains("All User")));

        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {

            @Override
            public Boolean apply(WebDriver d) {

                int tdIndex = 1;

                boolean isNameSaved = false;
                String findNameByIndex;

                while (!isNameSaved) {
                    findNameByIndex = "table#users > tbody > tr:nth-child(" + tdIndex + ") > td:first-child";
                    isNameSaved = d.findElement(By.cssSelector(findNameByIndex)).getText().contains(name);
                    tdIndex++;
                }

                return isNameSaved;

            }
        });

    }

    @Test
    public void C() throws Exception {

        // Check the title of the New User Page
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                return d.getTitle().contains("New User");
            }
        });
        
        // Check header contains New User
        String header = driver.findElement(By.cssSelector("div.page-header > h1")).getText();
        assertTrue((header.contains("New User")));

        // Check for Unique name
        // Add name to the name input [ previously added name ]
        WebElement formNameUnique = driver.findElement(By.id("name"));
        formNameUnique.sendKeys(name);
        
        // Check for Unique email
        // Add email to the email input [ previously added email ]
        WebElement formEmailUnique = driver.findElement(By.id("email"));
        formEmailUnique.sendKeys(email);

        // Submit the form
        WebElement submitButtonUniqueName = driver.findElement(By.cssSelector("div.form-actions > button"));
        submitButtonUniqueName.click();

        //Wait for Unique name error
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                return d.findElement(By.id("user.name.error")).getText().contains("Must be unique") &&
                       d.findElement(By.id("user.email.error")).getText().contains("Must be unique");
            }
        });

    }

}
