package testBase;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObject.Homepage;
import pageObject.Registerpage;

public class TC_001Homepage extends BaseTest {

    @Test(groups = {"sanity", "master"})
    public void verifyRegistration() {

        logger.info("*** Test execution started ***");

        Homepage hp = new Homepage(driver);
        hp.clickMyaccount();
        logger.info("*** My Account clicked ***");

      //  hp.clickRegister();
        logger.info("*** Register clicked ***");
        logger.debug("Current URL: " + driver.getCurrentUrl());

        Registerpage rp = new Registerpage(driver);
        rp.setFirstName("Arun");
        rp.setLastName("Kumar");
        rp.setEmail("arun" + System.currentTimeMillis() + "@gmail.com");
        rp.setTelephone("1234567890");
        rp.setPassword("Arun@1234");
        rp.setConfirmPassword("Arun@1234");
        rp.clickPolicy();
        rp.clickContinue();

        Assert.assertTrue(rp.isAccountCreated(), "Account confirmation message not displayed");
    }
}