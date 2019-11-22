package stepdefs;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static org.hamcrest.Matchers.containsInAnyOrder;

import java.util.*;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import services.CoinMarketAPICalls;

public class CoinMarketCapStepDefinations {

    private Response response;

    String currencyId;

    private String BASE_URL = "https://pro-api.coinmarketcap.com/v1/";
    private String ENDPOINT_GET_ID = "cryptocurrency/map";
    private String ENDPOINT_CONVERSION = "tools/price-conversion";

    private CoinMarketAPICalls coinMarketAPICalls = new CoinMarketAPICalls();
    private List<String> mineableCurrencies = new ArrayList<String>();

    @When("^I retrieve the ID of \"([^\"]*)\"$")
    public void iRetrieveTheIDOf(String currency) {
        currencyId = coinMarketAPICalls.getCurrencyID(currency);

    }

    @Then("^I should get the successful response$")
    public void iShouldGetTheSuccessfulResponse() {
    }

    @When("^I convert into Bolivian Boliviano using price Conversion API$")
    public void iConvertIntoBolivianBolivianoUsingPriceConversionAPI() {
        String convertToCurrency = "BOB";
        coinMarketAPICalls.covertInto(currencyId, convertToCurrency);
    }

    @Given("^I have to retrieve the Etherium info$")
    public void iHaveToRetrieveTheEtheriumInfo() {

        response = coinMarketAPICalls.getCurrencyInfoByCurrencyCode("ETH");
    }

    @When("^I request the Etherium Info from 'Info' API for the crypto currency$")
    public void iRequestTheEtheriumInfoFromInfoAPIForTheCryptoCurrency() {

    }

    @And("^Response data is successfully verified$")
    public void responseDataIsSuccessfullyVerified() {

    }

    @Given("^I retrieve first (\\d+) crypto currencies$")
    public void iRetrieveFirstCryptoCurrencies(int currencyCounter) {
        for (int i = 1; i <= currencyCounter; i++) {
            response = coinMarketAPICalls.getCurrencyInfoByCurrencyId(String.valueOf(i));
            getJsonResponse(i);
        }

    }


    @When("^I check for minable tags in the response$")
    public void iCheckForMinableTagsInTheResponse() {


    }

    @Then("^correct minable crypto currencies are displayed$")
    public void correctMinableCryptoCurrenciesAreDisplayed() {
        mineableCurrencies.forEach(System.out::println);

    }


    @And("^Response data should contain \"(.*)\",\"(.*)\",\"(.*)\",\"(.*)\",\"(.*)\" and \"(.*)\"$")
    public void responseDataShouldContainAnd(String logo, String technicalDoc, String symbol, String dateAdded, String platform, String tag) {
        String key = "ETH";
        HashMap<String, Object> jsonResponse = (HashMap<String, Object>) response.jsonPath().getMap("data").get(key);
        List<String> tags = (ArrayList<String>) jsonResponse.get("tags");


        assertEquals(jsonResponse.get("logo").toString().trim(), logo.trim());
        assertEquals(jsonResponse.get("symbol").toString().trim(), symbol.trim());
        assertNull(jsonResponse.get("platform"));
        assertEquals(jsonResponse.get("date_added").toString().trim(), dateAdded.trim());
        assertEquals(tags.get(0), tag.trim());

    }


    private void getJsonResponse(int i) {
        HashMap<String, Object> jsonResponse = (HashMap<String, Object>) response.jsonPath().getMap("data").get(String.valueOf(i));
        List<String> tags = (ArrayList<String>) jsonResponse.get("tags");

        if (tags.get(0).equalsIgnoreCase("mineable")) {

            mineableCurrencies.add(jsonResponse.get("symbol").toString());
        }
    }
}


