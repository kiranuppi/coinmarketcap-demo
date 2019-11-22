package services;

import static io.restassured.RestAssured.given;
import io.restassured.response.Response;



public class CoinMarketAPICalls {
    private String BASE_URL = "https://pro-api.coinmarketcap.com/v1/";
    private String ENDPOINT_GET_ID = "cryptocurrency/map";
    private String ENDPOINT_GET_INFO = "cryptocurrency/info";
    private String ENDPOINT_CONVERSION = "tools/price-conversion";

    public static String jsonAsString;

    Response response;

    public String getCurrencyID(String symbol) {

        response = given().
                header("X-CMC_PRO_API_KEY", "3503ca14-2a3b-4e22-ae2c-cb54d6f90870")
                .queryParam("symbol", symbol)

                .when()
                .get(BASE_URL + ENDPOINT_GET_ID)
                .then().statusCode(200)
                .extract().response();

        return response.jsonPath().getList("data.id").get(0).toString();


    }


    public void covertInto(String currencyId, String convertToCurrency) {

        response = given().
                header("X-CMC_PRO_API_KEY", "3503ca14-2a3b-4e22-ae2c-cb54d6f90870")
                .queryParam("id", currencyId)
                .queryParam("convert", convertToCurrency)
                .queryParam("amount", "50")
                .when()
                .get(BASE_URL + ENDPOINT_CONVERSION)
                .then().statusCode(200)
                .extract().response();
    }


    public Response getCurrencyInfoByCurrencyCode(String currency) {
        response = given().
                header("X-CMC_PRO_API_KEY", "3503ca14-2a3b-4e22-ae2c-cb54d6f90870")
                .queryParam("symbol", currency)
                .when()
                .get(BASE_URL + ENDPOINT_GET_INFO);
        return response;
    }

    public Response getCurrencyInfoByCurrencyId(String id) {
        response = given().
                header("X-CMC_PRO_API_KEY", "3503ca14-2a3b-4e22-ae2c-cb54d6f90870")
                .queryParam("id", id)
                .when()
                .get(BASE_URL + ENDPOINT_GET_INFO);
        return response;
    }
}
