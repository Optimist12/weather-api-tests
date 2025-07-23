package org.example.checker;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.support.FileUtils;
import org.example.model.ErrorResponse;
import org.example.model.WeatherResponse;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class WeatherResponseChecker {

    static ObjectMapper objectMapper = new ObjectMapper();

    public static void checkErrorCodeAndMessage(ErrorResponse actualResponse, Integer code, String message) {
        assertThat(actualResponse.error().code()).isEqualTo(code);
        assertThat(actualResponse.error().message()).isEqualTo(message);
    }

    public static void checkWeatherFields(WeatherResponse actual, String expectedFilename) {
        WeatherResponse expectedWeather;
        try {
            expectedWeather = objectMapper.readValue(FileUtils.getResourceFile("expected/" + expectedFilename), WeatherResponse.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertThat(actual).isEqualToComparingFieldByFieldRecursively(expectedWeather);
    }
}
