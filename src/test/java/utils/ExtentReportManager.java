package utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager implements ITestListener {

    private ExtentSparkReporter sparkReporter;
    private ExtentReports extent;
    private ExtentTest test;
    private String repName;

    private final String reportsDir = System.getProperty("user.dir") + File.separator + "Reports";
    private final String screenshotsDir = System.getProperty("user.dir") + File.separator + "screenshots";

    @Override
    public void onStart(ITestContext context) {

        // Create the Reports and screenshots folders if they don't exist
        new File(reportsDir).mkdirs();
        new File(screenshotsDir).mkdirs();

        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName = "Test-Report-" + timeStamp + ".html";

        sparkReporter = new ExtentSparkReporter(reportsDir + File.separator + repName);
        sparkReporter.config().setDocumentTitle("OpenCart Automation Report");
        sparkReporter.config().setReportName("OpenCart Functional Testing");
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        extent.setSystemInfo("Application", "OpenCart");
        extent.setSystemInfo("Operating System", System.getProperty("os.name"));
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));

        String os = context.getCurrentXmlTest().getParameter("os");
        String browser = context.getCurrentXmlTest().getParameter("browser");
        extent.setSystemInfo("OS", os);
        extent.setSystemInfo("Browser", browser);

        if (!context.getCurrentXmlTest().getIncludedGroups().isEmpty()) {
            extent.setSystemInfo("Groups", context.getCurrentXmlTest().getIncludedGroups().toString());
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getTestClass().getName() + " : " + result.getMethod().getMethodName());
        test.assignCategory(result.getMethod().getGroups());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.log(Status.PASS, result.getName() + " executed successfully");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.log(Status.FAIL, result.getName() + " failed");
        test.log(Status.INFO, String.valueOf(result.getThrowable()));

        try {
            String imgPath = captureScreen(result);
            if (imgPath != null) {
                test.addScreenCaptureFromPath(imgPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.log(Status.SKIP, result.getName() + " was skipped");
        test.log(Status.INFO, String.valueOf(result.getThrowable()));
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();

        // Optional: open the report in the browser automatically
        try {
            File report = new File(reportsDir + File.separator + repName);
            java.awt.Desktop.getDesktop().browse(report.toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Takes a screenshot by reading the "driver" field from the test class
    private String captureScreen(ITestResult result) throws IOException {

        WebDriver driver = null;
        try {
            Object testInstance = result.getInstance();
            Class<?> clazz = testInstance.getClass();
            Field field = null;
            while (clazz != null && field == null) {
                try {
                    field = clazz.getDeclaredField("driver");
                } catch (NoSuchFieldException e) {
                    clazz = clazz.getSuperclass();
                }
            }
            if (field != null) {
                field.setAccessible(true);
                driver = (WebDriver) field.get(testInstance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (driver == null) {
            return null;
        }

        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File target = new File(screenshotsDir + File.separator + result.getName() + "_" + timeStamp + ".png");
        target.getParentFile().mkdirs();
        Files.copy(source.toPath(), target.toPath());

        return target.getAbsolutePath();
    }
}