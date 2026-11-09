package testBase;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObject.Homepage;
import pageObject.Loginpage;
import pageObject.Myaccountpage;
import utils.DataProviders;

public class TC_003LoginTest extends BaseTest {

    @Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups = {"sanity", "regression", "master"})
    public void verify_LoginDDT(String email, String psw, String exp) {

        logger.info("Testing -> email: " + email + ", expected: " + exp);

        Homepage hp = new Homepage(driver);
        hp.clickMyaccount();
        hp.clickLogin();

        Loginpage lp = new Loginpage(driver);
        lp.Enteruser(email);
        lp.Enterpassword(psw);
        lp.clickLogin();

        Myaccountpage macc = new Myaccountpage(driver);
        boolean loggedIn = macc.verifypage();

        if (exp.equalsIgnoreCase("Valid")) {
            Assert.assertTrue(loggedIn, "Valid credentials but login failed: " + email);
            macc.clickLogout();                       // logout after a successful login
        } else {
            Assert.assertFalse(loggedIn, "Invalid credentials but login succeeded: " + email);
            Assert.assertTrue(lp.isWarningDisplayed(), "Warning message not displayed");
        }
    }
}