package org.iohk.setps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.iohk.helper.ApiHelper;
import org.json.JSONException;
import org.junit.Assert;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;


import java.io.IOException;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;

public class StepDefinitions {

    Response metadataResponse, propertiesResponse, postResponse;
    String subject,body;

    @Given("user has the correct {string} values")
    public void userHasTheCorrectSubjectValues(String subject) {
       this.subject = subject;
    }

    @When("the user makes a get request to the metadata service")
    public void theUserMakesAGetRequestToTheMetadataService() {
        metadataResponse = ApiHelper.getMetadata(subject);
    }

    @Then("he is able to get correct {string} for the provided values")
    public void heIsAbleToGetCorrectMetadataForTheProvidedValues(String metadata) throws IOException, JSONException {

        var expectedResponse = new String(readAllBytes(get("src/test/resources/response/"+metadata+".json")));
        JSONAssert.assertEquals(expectedResponse, metadataResponse.getBody().asString(), JSONCompareMode.LENIENT);
    }

    @And("the status code from api is {int}")
    public void theStatusCodeFromApiIs(int statusCode) {
      Assert.assertEquals(statusCode,metadataResponse.statusCode());
    }

    @Given("user has the incorrect subject values")
    public void userHasTheIncorrectSubjectValues() {
        subject = "incorrectSubject";
    }

    @When("the user makes a get request to the metadata service with incorrect value")
    public void theUserMakesAGetRequestToTheMetadataServiceWithIncorrectValue() {
        metadataResponse = ApiHelper.getMetadata("IncorrectSubject");
    }

    @Then("he receives an error message")
    public void heReceivesAnErrorMessage() {
        Assert.assertEquals("Requested subject 'IncorrectSubject' not found",metadataResponse.getBody().asString());
    }


    @When("the user makes a get properties request to the metadata service")
    public void theUserMakesAGetPropertiesRequestToTheMetadataService() {
        propertiesResponse = ApiHelper.getProperty(subject);
    }

    @And("he is able to receive correct {string} for the given subject")
    public void heIsAbleToReceiveCorrectPropertyForTheGivenSubject(String property) throws IOException, JSONException {

        var expectedResponse = new String(readAllBytes(get("src/test/resources/response/"+property+".json")));
        JSONAssert.assertEquals(expectedResponse, propertiesResponse.getBody().asString(), JSONCompareMode.LENIENT);
    }

    @Then("he receives a not found error message")
    public void heReceivesANotFoundErrorMessage() {
        Assert.assertEquals("Requested subject 'incorrectSubject' not found",propertiesResponse.getBody().asString());
    }

    @Then("the status code from the service is {int}")
    public void theStatusCodeFromTehServiceIs(int statusCode) {
        Assert.assertEquals(statusCode,propertiesResponse.statusCode());
    }

    @Given("user has the correct subject data")
    public void userHasTheCorrectSubjectData() throws IOException {
        body = new String(readAllBytes(get("src/test/resources/requestBody/subject1.json")));
    }

    @When("the user makes a post query request the metadata service")
    public void theUserMakesAPostQueryRequestTheMetadataService() {
      postResponse = ApiHelper.postQuery(body);
    }

    @And("the response from the service is a empty subject array")
    public void theResponseFromTheServiceIsAEmptySubjectArray() {
        Assert.assertEquals("{\"subjects\":[]}",postResponse.getBody().asString());
    }

    @Given("user has the correct subject and properties")
    public void userHasTheCorrectSubjectAndProperties() throws IOException {
        body = new String(readAllBytes(get("src/test/resources/requestBody/subject2.json")));
    }

    @Given("user has invalid request body for post query")
    public void userHasInvalidRequestBodyForPostQuery() throws IOException {
        body = new String(readAllBytes(get("src/test/resources/requestBody/invalidSubject.json")));
    }

    @Then("the user receives an error {string}")
    public void theUserReceivesAnError(String error) {
        Assert.assertEquals("<h1>"+error+"</h1>",postResponse.getBody().asString());
    }

    @Then("the status code from metadata service is {int}")
    public void theStatusCodeFromMetadataServiceIs(int status) {
        Assert.assertEquals(status,postResponse.statusCode());

    }
}
