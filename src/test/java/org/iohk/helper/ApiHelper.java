package org.iohk.helper;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ApiHelper {

    static String baseUrl = "http://metadata-server-mock.herokuapp.com";

    public static RequestSpecification buildSpecification() {

        return new RequestSpecBuilder()
                .setUrlEncodingEnabled(false)
                .addHeader("Content-Type", "application/json")
                .setBaseUri(baseUrl)
                .log(LogDetail.ALL)
                .build();
    }

    public static Response getProperty(String subject) {
        RestAssured.requestSpecification = buildSpecification();
        return given().pathParam("subject", subject)
                .when()
                .get("/metadata/{subject}/properties/name")
                .then().log().all()
                .extract().response();
    }

    public static Response getMetadata(String subject) {
        RestAssured.requestSpecification = buildSpecification();
        return given().pathParam("subject", subject)
                .when()
                .get("/metadata/{subject}")
                .then().log().all()
                .extract().response();
    }


}
