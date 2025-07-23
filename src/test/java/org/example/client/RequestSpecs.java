package org.example.client;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestSpecs {

    public static final String BASE_HOST = "http://localhost:8090";
    public static final String BASE_PATH = "v1/current.json";

    public static RequestSpecification baseSpec() {
        return new RequestSpecBuilder().build()
            .log().all()
            .contentType(ContentType.JSON)
            .relaxedHTTPSValidation()
            .baseUri(BASE_HOST)
            .basePath(BASE_PATH);
    }
}
