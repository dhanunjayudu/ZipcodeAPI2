package com.test.service.steps;

import com.test.service.ZipcodeService;
import org.jbehave.core.annotations.*;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@UsingSteps
public class ZipCodeApiSteps {

    private final ZipcodeService zipcodeService = new ZipcodeService();
    String actualPickedZipCode, distance, currentZip, targetZipCodes, multiDistanceURL, singleDistanceURL;

    @Given("multi distance and single distance urls")
    public void assignGivenUrls(@Named("multiDistanceURL") final String multiDistanceURL,
                                @Named("singleDistanceURL") final String singleDistanceURL) {
        this.multiDistanceURL = multiDistanceURL;
        this.singleDistanceURL = singleDistanceURL;
    }

    @Given("current zip code value")
    public void getCurrentZipValue(@Named("currentZip") final String currentZip) {
        this.currentZip = currentZip;
    }

    @Given("target zip code values")
    public void getTargetZipValue(@Named("targetZipCodes") final String targetZipCodes) {
        this.targetZipCodes = targetZipCodes;
    }

    @When("I check for first picked target zip code")
    public void retrieveFirstZip() throws IOException, InterruptedException {
        actualPickedZipCode = zipcodeService.pickFirstZip(multiDistanceURL, currentZip, targetZipCodes);
    }

    @Then("The expected picked zip code matches actual first picked zip code")
    public void testFirstPickedZipCode(@Named("expectedPickedZipCode") final String expectedPickedZipCode) {
        assertEquals(expectedPickedZipCode, actualPickedZipCode);
    }

    @When("retrieving distance between target zip to current zip")
    public void retrieveDistance() throws IOException, InterruptedException {
        distance = zipcodeService.retrieveDistance(singleDistanceURL, currentZip, targetZipCodes);
    }

    @When("retrieving distance between target zip to picked zip")
    public void retrieveDistanceFromPickedZip() throws IOException, InterruptedException {
        distance = zipcodeService.retrieveDistance(singleDistanceURL, currentZip, actualPickedZipCode);
    }

    @Then("Expected distance should match Actual distance")
    public void testShortDistance(@Named("expectedDistance") final String expectedDistance) {
        assertEquals(expectedDistance, distance);
    }

}
