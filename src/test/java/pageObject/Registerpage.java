package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Registerpage extends Baseclass {

    public Registerpage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "#input-firstname")
    WebElement txtFirstName;

    @FindBy(css = "#input-lastname")
    WebElement txtLastName;

    @FindBy(css = "#input-email")
    WebElement txtEmail;

    @FindBy(css = "#input-telephone")
    WebElement txtTelephone;

    @FindBy(css = "#input-password")
    WebElement txtPassword;

    @FindBy(css = "#input-confirm")
    WebElement txtConfirmPassword;

    @FindBy(xpath = "//input[@name='agree']")
    WebElement chkPolicy;

    @FindBy(xpath = "//input[@value='Continue']")
    WebElement btnContinue;

    @FindBy(xpath = "//h1[normalize-space()='Your Account Has Been Created!']")
    WebElement msgConfirmation;

    public void setFirstName(String fname) {
        txtFirstName.sendKeys(fname);
    }

    public void setLastName(String lname) {
        txtLastName.sendKeys(lname);
    }

    public void setEmail(String mail) {
        txtEmail.sendKeys(mail);
    }

    public void setTelephone(String tel) {
        txtTelephone.sendKeys(tel);
    }

    public void setPassword(String pwd) {
        txtPassword.sendKeys(pwd);
    }

    public void setConfirmPassword(String pwd) {
        txtConfirmPassword.sendKeys(pwd);
    }

    public void clickPolicy() {
        chkPolicy.click();
    }

    public void clickContinue() {
        btnContinue.click();
    }

    public boolean isAccountCreated() {
        try {
            return msgConfirmation.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

	public void ClickCheckbox() {
		chkPolicy.click();
		
	}

	public void ClickSubmit() {
		 btnContinue.click();
		
	}
}