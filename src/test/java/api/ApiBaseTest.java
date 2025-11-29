package api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import utils.ConfigReader;
import org.apache.http.HttpStatus;
import io.restassured.filter.log.LogDetail; // Loglama için gerekli

public class ApiBaseTest {

    protected static RequestSpecification requestSpec;
    protected static ResponseSpecification successResponseSpec;

    static {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(ConfigReader.getProperty("api.baseUrl"))
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        successResponseSpec = new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_OK)
                .expectContentType(ContentType.JSON)
                .build();
    }
}