package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AirQuality(Double co,
                         Double no2,
                         Double o3,
                         Double so2,
                         Double pm2_5,
                         String pm10,
                         @JsonProperty("us-epa-index")
                         Integer usEpaIndex,
                         @JsonProperty("gb-defra-index")
                         Integer gbDefraIndex
) {
}