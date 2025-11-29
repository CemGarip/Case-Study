package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import utils.ConfigReader;
import utils.DriverManager;
import utils.ScreenshotUtils;
import io.qameta.allure.Allure;
import java.io.ByteArrayInputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Hooks {
    private static final Logger logger = LogManager.getLogger(Hooks.class);
    private WebDriver driver;

    @Before("@UI")
    public void setUp(Scenario scenario) {
        logger.info("Setting up WebDriver for scenario: " + scenario.getName());
        driver = DriverManager.getDriver();
        driver.manage().window().maximize();
        driver.get(ConfigReader.getBaseUrl());
        logger.info("Navigated to: " + ConfigReader.getBaseUrl());
    }

    @After("@UI")
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            logger.error("Scenario Failed: " + scenario.getName());

            byte[] screenshotBytes = ScreenshotUtils.captureScreenshotAsBytes(driver);
            if (screenshotBytes != null) {
                Allure.addAttachment("Screenshot on Failure",
                        new ByteArrayInputStream(screenshotBytes));
            }
        }
        logger.info("Closing WebDriver...");
        DriverManager.quitDriver();
    }
}