package org.example.model;

public record Location(String name,
                       String region,
                       String country,
                       Double lat,
                       Double lon,
                       String tz_id,
                       Integer localtime_epoch,
                       String localtime
) {
}