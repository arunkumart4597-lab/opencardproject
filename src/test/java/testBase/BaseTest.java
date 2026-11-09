package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseTest {

    public WebDriver driver;
    public Logger logger;
    public Properties p;

    @Parameters({"os", "browser"})
    @BeforeClass(groups = {"sanity", "regression", "master"})
    public void browserlaunch(String os, String br) throws IOException {

        // Load config.properties
        try (FileReader fr = new FileReader(
                System.getProperty("user.dir") + "/src/test/resources/config.properties")) {
            p = new Properties();
            p.load(fr);
        }

        logger = LogManager.getLogger(this.getClass());

        switch (br.toLowerCase()) {
        case "chrome":
            driver = new ChromeDriver();
            break;
        case "edge":
            driver = new EdgeDriver();
            break;
        case "firefox":
            driver = new FirefoxDriver();
            break;
        default:
            throw new IllegalArgumentException("Invalid browser: " + br);
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(p.getProperty("appurl"));
    }

    @AfterClass(groups = {"sanity", "regression", "master"})
    public void closebrowser() {
        if (driver != null) {
            driver.quit();
        }
    }

    public String captureScreen(String tname) throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        File dir = new File(System.getProperty("user.dir") + "/test-output/screenshots");
        dir.mkdirs();

        String targetFilePath = dir.getAbsolutePath() + "/" + tname + "_" + timeStamp + ".png";
        sourceFile.renameTo(new File(targetFilePath));

        return targetFilePath;
    }
}