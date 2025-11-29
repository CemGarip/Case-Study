package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.By;


public class HomePage extends BasePage {


    @FindBy(css = "#onetrust-accept-btn-handler")
    private WebElement cookieAcceptButton;

    @FindBy(css = "[data-testid='search-one-way-label']")
    private WebElement oneWayRadioButton;

    @FindBy(css = "[data-testid='flight-origin-input-comp']")
    private WebElement originInputButton;

    @FindBy(css = "[data-testid='endesign-flight-origin-autosuggestion-input']")
    private WebElement originAutosuggestionInput;

    @FindBy(css = "[data-testid='endesign-flight-origin-autosuggestion-option-item-0']")
    private WebElement originFirstOption;

    @FindBy(css = "[data-testid='flight-destination-input-comp']")
    private WebElement destinationInputButton;

    @FindBy(css = "[data-testid='endesign-flight-destination-autosuggestion-input']")
    private WebElement destinationAutosuggestionInput;

    @FindBy(css = "[data-testid='endesign-flight-destination-autosuggestion-option-item-0']")
    private WebElement destinationFirstOption;

    @FindBy(css = "[data-testid='enuygun-homepage-flight-departureDate-datepicker-input']")
    private WebElement departureDateInput;

    @FindBy(css = "[data-testid='enuygun-homepage-flight-returnDate-label']")
    private WebElement returnDateLabel;

    @FindBy(xpath = "(//div[contains(@class, 'jHaclP')])[last()]")
    private WebElement departureNextMonthButton;

    @FindBy(css = "[data-testid='enuygun-homepage-flight-returnDate-month-forward-button']")
    private WebElement returnNextMonthButton;

    @FindBy(css = "[data-testid='enuygun-homepage-flight-submitButton']")
    private WebElement searchButton;

    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }


    public void handleCookiePopup() {
        try {
            logger.info("Attempting to close cookie consent popup on HomePage...");

            WebElement acceptButton = waitUtils.waitForElementVisible(cookieAcceptButton);

            if (acceptButton != null) {
                js.executeScript("arguments[0].click();", acceptButton);
                logger.info("Cookie popup clicked via JS click.");

                waitUtils.waitForElementInvisible(cookieAcceptButton);
                logger.info("Cookie popup closed successfully.");
            }

        } catch (Exception e) {
            logger.warn("Cookie click failed (or timeout). Attempting to remove with JS. Error: " + e.getMessage());

            try {
                WebElement container = driver.findElement(By.id("onetrust-banner-sdk-root"));
                js.executeScript("arguments[0].remove();", container);
                logger.info("Cookie popup container removed successfully using JavaScript as backup.");
            } catch (Exception ex) {
                logger.info("Cookie container not found or could not be removed. Proceeding.");
            }
        }
    }

    public void enterOrigin(String city) {
        click(originInputButton);
        logger.info("Clicked on origin input to open autosuggestion");

        waitUtils.waitForElementVisible(originAutosuggestionInput);
        type(originAutosuggestionInput, city);
        logger.info("Entered origin city: " + city);

        waitUtils.waitForElementVisible(originFirstOption);
        click(originFirstOption);
        logger.info("Selected first option from origin dropdown");

    }

    public void enterDestination(String city) {
        click(destinationInputButton);
        logger.info("Clicked on destination input to open autosuggestion");

        waitUtils.waitForElementVisible(destinationAutosuggestionInput);

        // 🚨 İYİLEŞTİRME 🚨: Önce alanı temizle, sonra metni yavaşça yaz.
        destinationAutosuggestionInput.clear();
        type(destinationAutosuggestionInput, city);
        logger.info("Entered destination city: " + city);

        // 🚨 KRİTİK GÜVENLİK BEKLEMESİ 🚨: Önerilerin yüklenmesi için statik bekleme (300-500ms).
        // Explicit Wait'in bile yetersiz kaldığı durumlarda bu gereklidir.
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        waitUtils.waitForElementVisible(destinationFirstOption); // Max 10s beklenir
        click(destinationFirstOption);
        logger.info("Selected first option from destination dropdown");
    }

    public void selectDepartureDate(String departureDateStr, String targetMonthYear) {
        click(departureDateInput);
        logger.info("Clicked on departure date input to open calendar");

        String formattedDate = convertGherkinDateToTitle(departureDateStr);

        By monthLabelBy = By.cssSelector("[data-testid='enuygun-homepage-flight-departureDate-month-name-and-year']");
        navigateToMonth(targetMonthYear, monthLabelBy, departureNextMonthButton);

        By departureDayButton = By.cssSelector("button[title='" + formattedDate + "'][data-testid='datepicker-active-day']");

        WebElement dayButtonElement = driver.findElement(departureDayButton);

        waitUtils.waitForElementVisible(dayButtonElement);
        click(dayButtonElement);
        logger.info("Selected departure date: " + formattedDate);
    }
    private String convertGherkinDateToTitle(String dateStr) {

        if (dateStr.contains("-")) {
            return dateStr;
        }

        try {
            String[] parts = dateStr.split("\\."); // Örn: 15.01.2026'yı ayırır.
            return parts[2] + "-" + parts[1] + "-" + parts[0];
        } catch (Exception e) {
            logger.error("ERROR: Failed to convert date format for: " + dateStr + ". Returning empty string.");
            return "";
        }
    }
    public void selectReturnDate(String formattedDate, String targetMonthYear) {
        waitUtils.waitForElementVisible(returnDateLabel);
        click(returnDateLabel);
        logger.info("Clicked on return date label to open calendar");

        By returnMonthLabelBy = By.cssSelector("[data-testid='enuygun-homepage-flight-returnDate-month-name-and-year']");

        navigateToMonth(targetMonthYear, returnMonthLabelBy, returnNextMonthButton);

        By returnDayButton = By.cssSelector("button[title='" + formattedDate + "'][data-testid='datepicker-active-day']");
        waitUtils.waitForElementVisible(driver.findElement(returnDayButton));
        click(driver.findElement(returnDayButton));
        logger.info("Selected return date: " + formattedDate);
    }

    private void navigateToMonth(String targetMonthYear, By monthLabelLocator, WebElement nextButton) {
        int maxAttempts = 24;
        int attempts = 0;
        String previousMonths = "";

        if (isTargetMonthVisible(targetMonthYear, monthLabelLocator)) {
            logger.info("Target month already visible: " + targetMonthYear);
            return;
        }

        while (attempts < maxAttempts) {
            String currentMonths = getCurrentVisibleMonths(monthLabelLocator);

            click(nextButton);
            logger.info("Clicked next month button, attempt: " + (attempts + 1));
            attempts++;

            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            if (isTargetMonthVisible(targetMonthYear, monthLabelLocator)) {
                logger.info("Target month found after " + attempts + " clicks: " + targetMonthYear);
                return;
            }

            String newMonths = getCurrentVisibleMonths(monthLabelLocator);
            if (currentMonths.equals(newMonths) && !previousMonths.isEmpty()) {
                logger.warn("Calendar not advancing (reached max limit). Current: " + currentMonths);
                break;
            }
            previousMonths = currentMonths;
        }

        logger.warn("Could not find target month: " + targetMonthYear);
    }

    private String getCurrentVisibleMonths(By monthLabelLocator) {
        try {
            java.util.List<org.openqa.selenium.WebElement> monthLabels =
                    driver.findElements(monthLabelLocator);
            StringBuilder months = new StringBuilder();
            for (org.openqa.selenium.WebElement label : monthLabels) {
                months.append(label.getText()).append("|");
            }
            return months.toString();
        } catch (Exception e) {
            return "";
        }
    }

    private boolean isTargetMonthVisible(String targetMonthYear, By monthLabelLocator) {
        try {
            java.util.List<org.openqa.selenium.WebElement> monthLabels =
                    driver.findElements(monthLabelLocator);

            for (org.openqa.selenium.WebElement label : monthLabels) {
                String monthText = label.getText().trim();
                logger.info("Checking calendar month: '" + monthText + "' vs target: '" + targetMonthYear + "'");

                if (monthText.equalsIgnoreCase(targetMonthYear)) {
                    logger.info("MATCH FOUND! (case insensitive)");
                    return true;
                }
            }
            logger.info("No match found in visible months");
            return false;
        } catch (Exception e) {
            logger.error("Error checking target month: " + e.getMessage());
            return false;
        }
    }


    public void selectOneWayTrip() {
        try {
            logger.info("Selecting one-way trip...");

            waitUtils.waitForElementVisible(oneWayRadioButton);

            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", oneWayRadioButton);

            js.executeScript("arguments[0].click();", oneWayRadioButton);

            logger.info("One-way trip selected successfully");

        } catch (Exception e) {
            logger.error("Error selecting one-way trip: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void clickSearchButton() {
        waitUtils.waitForElementClickable(searchButton);
        click(searchButton);
        logger.info("Clicked search button - navigating to flight results");
    }
}