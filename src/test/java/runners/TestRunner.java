package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features", // Directory where feature files are located
        glue = {"stepdefinitions", "hooks"}, // Packages containing Step Definitions and Hooks
        tags = "@UI", // Tag to filter and execute specific scenarios
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber-html-report.html",
                "json:target/cucumber-reports/cucumber.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm" // Allure reporting integration
        },
        monochrome = true // Makes the console output readable
)
public class TestRunner extends AbstractTestNGCucumberTests {

    // Required for parallel test execution (optional)
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}