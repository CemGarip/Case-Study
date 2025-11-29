package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import pages.HomePage;
import pages.FlightListPage;
import utils.ConfigReader;
import utils.DriverManager;
import utils.FlightAnalyzer;
import utils.FlightData;
import java.util.List;
import java.util.Map;

public class FlightSearchSteps {

    private HomePage homePage;
    private FlightListPage flightListPage;

    public FlightSearchSteps() {
        this.homePage = new HomePage(DriverManager.getDriver());
    }


    @Given("user navigates to {string} homepage")
    public void userNavigatesToHomepage(String urlKey) {
        homePage.handleCookiePopup();
    }

    @And("user selects round-trip travel type")
    public void userSelectsRoundTripTravelType() {
    }

    @And("user enters {string} as origin and {string} as destination")
    public void userEntersAsOriginAndAsDestination(String originCity, String destinationCity) {
        homePage.enterOrigin(originCity);
        homePage.enterDestination(destinationCity);
    }

    @And("user selects {string} as departure and {string} as return date")
    public void userSelectsAsDepartureAndAsReturnDate(String departureDateStr, String returnDateStr) {
        String targetMonthYear = ConfigReader.getDepartureMonthYear();

        homePage.selectDepartureDate(departureDateStr, targetMonthYear);

        if (returnDateStr != null && !returnDateStr.isEmpty()) {
            String formattedReturnDate = ConfigReader.getFormattedReturnDate();
            String returnMonthYear = ConfigReader.getReturnMonthYear();
            homePage.selectReturnDate(formattedReturnDate, returnMonthYear);
        }
    }


    @When("user clicks the search button")
    public void userClicksTheSearchButton() {
        homePage.clickSearchButton();
        this.flightListPage = new FlightListPage(DriverManager.getDriver());
    }

    @And("user applies time filter if scenario is {string}")
    public void userAppliesTimeFilterIfScenarioIs(String testType) {
        if (testType.startsWith("Part1")) {
            flightListPage.setDepartureTimeFilter(10, 18);
        }
    }

    @And("user applies Turkish Airlines filter and sorts if scenario is {string}")
    public void userAppliesTurkishAirlinesFilterAndSortsIfScenarioIs(String testType) {
        if (testType.equals("Part1_Price")) {
            flightListPage.openAirlineFilter();
            flightListPage.selectTurkishAirlines();
            flightListPage.sortByPriceAscending();
        }
    }

    @Then("prices are verified to be sorted from lowest to highest if scenario is {string}")
    public void pricesAreVerifiedToBeSortedFromLowestToHighestIfScenarioIs(String testType) {
        if (testType.equals("Part1_Price")) {
            Map<String, Object> verificationResult = flightListPage.verifyPriceSortingAccuracy();
            boolean isPriceSortingCorrect = (boolean) verificationResult.get("success");

            Assert.assertTrue(isPriceSortingCorrect,
                    "Case 2: Fiyat sıralaması doğru değil!");
        }
    }

    @Then("flight data is extracted, saved to CSV, and analyzed if scenario is {string}")
    public void flightDataIsExtractedSavedToCSVAndAnalyzedIfScenarioIs(String testType) {
        if (testType.equals("Part4_Analysis")) {
            String route = "İstanbul-Lefkoşa"; // Rotayı direkt olarak ayarla
            String date = ConfigReader.getDepartureDate();

            List<FlightData> flightDataList = flightListPage.extractAndSaveFlightData(route, date);

            if (!flightDataList.isEmpty()) {
                FlightAnalyzer.analyzePricesByAirline(flightDataList);
                FlightAnalyzer.findMostCostEffective(flightDataList);
                FlightAnalyzer.visualizePriceDistribution(flightDataList);
            } else {
                System.err.println("❌ Hata: Uçuş listesinden veri çıkarılamadı veya boş.");
            }
        }
    }

    @And("user selects one-way travel type")
    public void userSelectsOneWayTravelType() {
        homePage.selectOneWayTrip();
    }


    @And("user only selects {string}")
    public void userOnlySelects(String departureDateKey) {
        String formattedDepartureDate = ConfigReader.getFormattedOneWayDepartureDate();
        String departureMonthYear = ConfigReader.getOneWayDepartureMonthYear();
        homePage.selectDepartureDate(formattedDepartureDate, departureMonthYear);
    }

    @And("user selects direct flights only from the transit filter")
    public void userSelectsDirectFlightsOnlyFromTheTransitFilter() {
        flightListPage.openTransitFilter();
        flightListPage.selectDirectFlights();
    }

    @And("user sets departure time filter between {int} and {int}")
    public void userSetsDepartureTimeFilterBetween(int startHour, int endHour) {
        flightListPage.setDepartureTimeFilter(startHour, endHour);
    }

    @And("user selects {string} from the airline filter")
    public void userSelectsFromTheAirlineFilter(String airlineName) {
        flightListPage.openAirlineFilter();
        if (airlineName.equalsIgnoreCase("Turkish Airlines")) {
            flightListPage.selectTurkishAirlines();
        } else if (airlineName.equalsIgnoreCase("All Airlines")) {
            flightListPage.selectAllAirlines();
        }
    }

    @And("user selects {string} and {string} airports from the airport filter")
    public void userSelectsAndAirportsFromTheAirportFilter(String airport1, String airport2) {
        flightListPage.openAirportFilter();
        if (airport1.equalsIgnoreCase("SAW") || airport2.equalsIgnoreCase("SAW")) {
            flightListPage.selectSawAirport();
        }
        if (airport1.equalsIgnoreCase("IST") || airport2.equalsIgnoreCase("IST")) {
            flightListPage.selectIstAirport();
        }
    }

    @And("user sorts the results by price in ascending order")
    public void userSortsTheResultsByPriceInAscendingOrder() {
        flightListPage.sortByPriceAscending();
    }

    @Then("the prices are verified to be sorted from lowest to highest")
    public void thePricesAreVerifiedToBeSortedFromLowestToHighest() {
        Map<String, Object> verificationResult = flightListPage.verifyPriceSortingAccuracy();
        boolean isPriceSortingCorrect = (boolean) verificationResult.get("success");
        Assert.assertTrue(isPriceSortingCorrect, "Fiyat sıralaması doğru değil!");
    }

    @Then("scrolling to the top of the page is successful")
    public void scrollingToTheTopOfThePageIsSuccessful() {
        flightListPage.scrollToTop();
    }
}