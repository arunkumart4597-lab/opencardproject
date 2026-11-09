package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Myaccountpage extends Baseclass {

    public Myaccountpage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//h2[normalize-space()='My Account']")
    WebElement loginheader;

    @FindBy(xpath = "//span[text()='My Account']")
    WebElement Myaccount;

    @FindBy(xpath = "//ul[contains(@class,'dropdown-menu')]//a[normalize-space()='Logout']")
    WebElement lnklogout;

    public boolean verifypage() {
        try {
            return loginheader.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickLogout() {
        Myaccount.click();
        lnklogout.click();
    }
}