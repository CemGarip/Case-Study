package base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WaitUtils;

public class BasePage {
    protected WebDriver driver;
    protected WaitUtils waitUtils;
    protected JavascriptExecutor js;
    protected Logger logger;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
        this.js = (JavascriptExecutor) driver;
        this.logger = LogManager.getLogger(this.getClass());
    }

    protected WebElement findElement(WebElement element) {
        return waitUtils.waitForElementVisible(element);
    }

    protected void click(WebElement element) {
        waitUtils.waitForElementClickable(element).click();
        logger.info("Clicked on element: " + element);
    }

    protected void type(WebElement element, String text) {
        WebElement el = findElement(element);
        el.clear();
        el.sendKeys(text);
        logger.info("Typed text: " + text + " into element: " + element);
    }
}