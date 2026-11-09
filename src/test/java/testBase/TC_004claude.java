package testBase;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObject.Homepage;
import pageObject.Registerpage;

public class TC_004claude extends BaseTest {

    @Test(groups = {"regression", "master"})
    public void Homepagetest() {

        try {
            logger.info("******* Test case execution started *******");

            Homepage hp = new Homepage(driver);
            hp.clickMyaccount();
            logger.info("******* Clicked on My Account link *******");

          //  hp.clickRegister();
            logger.info("******* Clicked on Register link *******");
            logger.debug("Current URL: " + driver.getCurrentUrl());

            Registerpage rp = new Registerpage(driver);
            Assert.assertTrue(driver.getCurrentUrl().contains("register"),
                    "Register page did not open");

            rp.setFirstName("Arun");
            rp.setLastName("Kumar");
            rp.setEmail("arun" + System.currentTimeMillis() + "@gmail.com");
            rp.setTelephone("1234567890");
            rp.setPassword("Arun@1234");
            rp.setConfirmPassword("Arun@1234");
            rp.clickPolicy();
            rp.clickContinue();

            Assert.assertTrue(rp.isAccountCreated(), "Account creation confirmation not displayed");

        } catch (Exception e) {
            logger.error("Test failed: " + e.getMessage());
            Assert.fail();
        }

        logger.info("******* Test case execution finished *******");
    }
}