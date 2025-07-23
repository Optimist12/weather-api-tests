package org.example.client;

import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import org.example.model.ErrorResponse;
import org.example.model.WeatherResponse;

import static io.restassured.RestAssured.given;

public class WeatherApiClient {

    public static final String key = "126ff74b6a3146cbadf102536251807";

    RestAssuredConfig restAssuredConfig;

    public WeatherApiClient() {
        restAssuredConfig = RestAssuredConfig.config()
            .logConfig(LogConfig.logConfig()
                .enablePrettyPrinting(true)
                .enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL));
        RestAssured.requestSpecification = RequestSpecs.baseSpec();
    }

    public WeatherResponse getWeatherByCity(String city) {
        return given()
            .queryParams("q", city, "key", key)
            .get()
            .then().log().all()
            .assertThat()
            .statusCode(200)
            .extract()
            .as(WeatherResponse.class);
    }

    public ErrorResponse getWeatherByCityWithError(String city, String key, Integer httpCode) {
        return given()
            .queryParams("q", city, "key", key)
            .get()
            .then().log().all()
            .assertThat()
            .statusCode(httpCode)
            .extract()
            .as(ErrorResponse.class);
    }
}
