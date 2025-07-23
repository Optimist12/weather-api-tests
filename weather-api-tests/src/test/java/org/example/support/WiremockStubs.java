package org.example.support;

import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.jsonResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.example.support.FileUtils.getResourceFile;

public class WiremockStubs {
    WireMockServer wireMockServer = new WireMockServer(options().port(8090));

    private void addStubs() {
        wireMockServer.stubFor(get("/v1/current.json?q=Paris&key=126ff74b6a3146cbadf102536251807")
            .willReturn(okJson(getStubFromRes("stubs/paris_weather.json"))));
        wireMockServer.stubFor(get("/v1/current.json?q=Moscow&key=126ff74b6a3146cbadf102536251807")
            .willReturn(okJson(getStubFromRes("stubs/moscow_weather.json"))));
        wireMockServer.stubFor(get("/v1/current.json?q=Tokio&key=126ff74b6a3146cbadf102536251807")
            .willReturn(okJson(getStubFromRes("stubs/tokio_weather.json"))));
        wireMockServer.stubFor(get("/v1/current.json?q=Madrid&key=126ff74b6a3146cbadf102536251807")
            .willReturn(okJson(getStubFromRes("stubs/madrid_weather.json"))));
        wireMockServer.stubFor(get("/v1/current.json?q=Paris&key=iq7by0d2yfrn19xd5qz1uxfa329yv9s")
            .willReturn(jsonResponse(getStubFromRes("stubs/api_key_exceeded.json"), 403)));
        wireMockServer.stubFor(get("/v1/current.json?q=Paris&key=r6wq3umf2czx6yg0u6p6r8vf23wxdkm")
            .willReturn(jsonResponse(getStubFromRes("stubs/api_key_disabled.json"), 403)));
        wireMockServer.stubFor(get("/v1/current.json?q=Paris&key=123")
            .willReturn(jsonResponse(getStubFromRes("stubs/api_key_invalid.json"), 401)));
        wireMockServer.stubFor(get("/v1/current.json?q=UnknownCity&key=126ff74b6a3146cbadf102536251807")
            .willReturn(jsonResponse(getStubFromRes("stubs/location_not_found.json"), 400)));

    }

    @SneakyThrows
    private String getStubFromRes(String path) {
        InputStream resourceFile = getResourceFile(path);
        return new String(resourceFile.readAllBytes(), StandardCharsets.UTF_8);
    }

    public void stopWiremock() {
        wireMockServer.resetMappings();
        wireMockServer.stop();
    }
    public void startWiremock() {
        wireMockServer.start();
        addStubs();
    }


}
