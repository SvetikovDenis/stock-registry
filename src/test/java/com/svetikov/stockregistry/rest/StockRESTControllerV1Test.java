package com.svetikov.stockregistry.rest;

import com.svetikov.stockregistry.config.SpringConfig;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringConfig.class})
@TestExecutionListeners()
public class StockRESTControllerV1Test {

    private final String STOCK_API_URL = "/api/v1/stock";
    private final String STOCK_ARRAY_JSON_SCHEMA = "schemas/stock_array_dto_schema.json";
    private final String STOCK_JSON_SCHEMA = "schemas/stock_dto_schema.json";
    private final String TEST_STOCK_ID = "1";
    static final String DUMMY_TEST_JSON = "{ \"Test\": \"Test\" }";


    @Test
    public void checkGetAllStocksStatusCode_Then200() {
        get(STOCK_API_URL).
                then().
                statusCode(200);
    }

    @Test
    public void validateGetAllStockDTOsSchema() {
        get(STOCK_API_URL).
                then().
                assertThat().
                body(matchesJsonSchemaInClasspath(STOCK_ARRAY_JSON_SCHEMA));
    }

    @Test
    public void checkResponseTimeGetAll() {
        get(STOCK_API_URL).
                then().
                time(lessThan(2000L));
    }

    @Test
    public void getAllStocksWithSize0_ShouldReturnEmptyList() {
        get(STOCK_API_URL + "?page=0&size=0").
                then().
                body("", hasSize(0));
    }

    @Test
    public void getStockByIdTest() {
        get(STOCK_API_URL + "/" + TEST_STOCK_ID).
                then().
                body(matchesJsonSchemaInClasspath(STOCK_JSON_SCHEMA));
    }

    @Test
    public void createStockDummyValue_ShouldReturn400Error() {
        given().
                contentType(ContentType.JSON).
                body(DUMMY_TEST_JSON).
                when().
                post(STOCK_API_URL).
                then().
                statusCode(400);
    }


}
