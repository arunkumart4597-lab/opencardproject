package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Loginpage extends Baseclass {

    public Loginpage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "#input-email")
    WebElement txtusername;

    @FindBy(css = "#input-password")
    WebElement txtpassword;

    @FindBy(css = "input[value='Login']")
    WebElement btnlogin;

    @FindBy(css = ".alert-danger")
    WebElement msgWarning;

    public void Enteruser(String uname) {
        txtusername.clear();
        txtusername.sendKeys(uname);
    }

    public void Enterpassword(String pwd) {
        txtpassword.clear();
        txtpassword.sendKeys(pwd);
    }

    public void clickLogin() {
        btnlogin.click();
    }

    public boolean isWarningDisplayed() {
        try {
            return msgWarning.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}