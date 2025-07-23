package org.example


import org.example.checker.WeatherResponseChecker
import org.example.client.WeatherApiClient
import org.example.support.WiremockStubs
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class WeatherTests extends Specification {

    @Shared
    WiremockStubs wiremockStubs = new WiremockStubs();
    @Shared
    WeatherApiClient apiClient = new WeatherApiClient();

    def "Успешный тест получения текущей погоды для города. Проверка всех полей в ответе"(String city, String expected) {
        when: "Я вызываю GET v1/current.json/ c параметром city = #city "
        def actualWeatherByCity = apiClient.getWeatherByCity(city)
        then: "Поля в ответе от сервера совпадают с ожидаемыми"
        WeatherResponseChecker.checkWeatherFields(actualWeatherByCity, expected);
        where:
        city     | expected
        "Paris"  | "paris_weather.json"
        "Moscow" | "moscow_weather.json"
        "Tokio"  | "tokio_weather.json"
        "Madrid"  | "madrid_weather.json"
    }

    @Unroll
    def "Неуспешный вызов метода получения текущей погоды. Некорректные параметры запроса"(String message, String city, String apiKey, int httpCode, int errorCode) {
        when: "Я вызываю GET v1/current.json/ c параметрами apiKey = #apiKey и city = #city "
        def actualWeatherByCity = apiClient.getWeatherByCityWithError(city, apiKey, httpCode)
        then: "Я получаю ответ с полями code = #errorCode и message = #message"
        WeatherResponseChecker.checkErrorCodeAndMessage(actualWeatherByCity, errorCode, message)
        where:
        message                                       | city          | apiKey                            | httpCode | errorCode
        "No matching location found."                 | "UnknownCity" | "126ff74b6a3146cbadf102536251807" | 400      | 1006
        "API key is invalid or not provided."         | "Paris"       | "123"                             | 401      | 1002
        "API key has exceeded calls per month quota." | "Paris"       | "iq7by0d2yfrn19xd5qz1uxfa329yv9s" | 403      | 2007
        "API key has been disabled."                  | "Paris"       | "r6wq3umf2czx6yg0u6p6r8vf23wxdkm" | 403      | 2008
    }


    def cleanupSpec() {
        wiremockStubs.stopWiremock()
    }

    def setupSpec() {
        wiremockStubs.startWiremock()
    }
}