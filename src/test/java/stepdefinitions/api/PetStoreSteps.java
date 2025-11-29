package stepdefinitions.api;

import api.ApiBaseTest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class PetStoreSteps extends ApiBaseTest {

    private Response response;
    private String petId;
    private String petName;
    private String requestBody;

    @Given("Pet details are prepared with ID: {string} and name: {string}")
    public void petDetailsArePreparedWithIdAndName(String id, String name) {
        this.petId = id;
        this.petName = name;
        requestBody = "{\"id\": " + id + ", \"name\": \"" + name + "\", \"status\": \"available\"}";
    }

    @Given("Pet details are prepared with only name: {string}")
    public void petDetailsArePreparedWithOnlyName(String name) {
        requestBody = "{\"name\": \"" + name + "\", \"status\": \"available\"}";
    }

    @Given("the previously created Pet ID {string} is available")
    public void thePreviouslyCreatedPetIDIsAvailable(String id) {
        this.petId = id;
    }

    @Given("a non-existent Pet ID: {string}")
    public void aNonExistentPetID(String id) {
        this.petId = id;
    }

    @Given("the existing Pet ID {string} is available")
    public void theExistingPetIDIsAvailable(String id) {
        this.petId = id;
    }

    @Given("new name is prepared as {string}")
    public void newNameIsPreparedAs(String newName) {
        this.petName = newName;
        requestBody = "{\"id\": " + this.petId + ", \"name\": \"" + newName + "\", \"status\": \"sold\"}";
    }

    @Given("the deleted Pet ID {string} is available")
    public void theDeletedPetIDIsAvailable(String id) {
        this.petId = id;
    }


    @When("a POST request is sent to the {string} endpoint")
    public void aPostRequestIsSentToTheEndpoint(String endpoint) {
        response = given().spec(requestSpec)
                .body(requestBody)
                .when().post(endpoint);
    }

    @When("a GET request is sent to the {string} endpoint")
    public void aGetRequestIsSentToTheEndpoint(String endpoint) {
        response = given().spec(requestSpec)
                .pathParam("petId", petId)
                .when().get(endpoint);
    }

    @When("a PUT request is sent to the {string} endpoint")
    public void aPutRequestIsSentToTheEndpoint(String endpoint) {
        response = given().spec(requestSpec)
                .body(requestBody)
                .when().put(endpoint);
    }

    @When("a DELETE request is sent to the {string} endpoint")
    public void aDeleteRequestIsSentToTheEndpoint(String endpoint) {
        response = given().spec(requestSpec)
                .pathParam("petId", petId)
                .when().delete(endpoint);
    }



    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatusCode) {
        response.then().log().ifValidationFails().statusCode(expectedStatusCode);
    }

    @And("the response body should contain the Pet ID {string} and name {string}")
    public void theResponseBodyShouldContainThePetIDAndName(String expectedId, String expectedName) {
        response.then().body("id", equalTo(Integer.parseInt(expectedId)))
                .body("name", equalTo(expectedName));
    }

    @And("the pet name should be {string}")
    public void thePetNameShouldBe(String expectedName) {
        response.then().body("name", equalTo(expectedName));
    }

    @And("the updated pet name in the response should be {string}")
    public void theUpdatedPetNameInTheResponseShouldBe(String expectedName) {
        response.then().body("name", equalTo(expectedName));
    }

    @And("the message should be {string}")
    public void theMessageShouldBe(String expectedMessage) {
        response.then().body("message", equalTo(expectedMessage));
    }

    @And("the error message should be {string}")
    public void theErrorMessageShouldBe(String expectedMessage) {
        response.then().body("message", equalTo(expectedMessage));
    }

    @And("the response body schema should be valid")
    public void theResponseBodySchemaShouldBeValid() {
        response.then().assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/pet-schema.json"));
    }
}