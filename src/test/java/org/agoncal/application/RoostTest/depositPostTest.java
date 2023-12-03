// Test generated by RoostGPT for test test-RA-dec1 using AI Type Open AI and AI Model gpt-4

// Test generated for /deposit_post for http method type POST in rest-assured framework

// RoostTestHash=70db6d5eac

package org.agoncal.application.RoostTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DepositPostTest {

    private static final String BASE_URL = System.getenv("BASE_URL");
    private static final String CSV_FILE_PATH = "src/test/java/org/agoncal/application/RoostTest/deposit_post.csv";

    @Test
    public void depositPostTest() {
        RestAssured.baseURI = BASE_URL;
        processCsvFile(CSV_FILE_PATH);
    }

    private void processCsvFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String headerLine = reader.readLine();
            String[] headers = headerLine.split(",");

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Map<String, String> map = createMapFromCsvData(headers, data);
                processDepositPostRequest(map);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, String> createMapFromCsvData(String[] headers, String[] data) {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < headers.length; i++) {
            map.put(headers[i], data[i]);
        }
        return map;
    }

    private void processDepositPostRequest(Map<String, String> map) {
        Response response = given()
                .pathParam("koreUserId", map.get("koreUserId"))
                .pathParam("botId", map.get("botId"))
                .pathParam("accountId", map.get("accountId"))
                .pathParam("Authorization", map.get("Authorization"))
                .contentType(ContentType.JSON)
                .body(createRequestBody(map))
                .when()
                .post("/deposit")
                .then()
                .extract().response();

        validateResponse(response);
    }

    private String createRequestBody(Map<String, String> map) {
        return "{\n" +
                "  \"customerId\": \"" + map.get("customerId") + "\",\n" +
                "  \"sourceAccountId\": \"" + map.get("sourceAccountId") + "\",\n" +
                "  \"amount\": \"" + map.get("amount") + "\",\n" +
                "  \"currency\": \"" + map.get("currency") + "\",\n" +
                "  \"image\": \"" + map.get("image") + "\n" +
                "}";
    }

    private void validateResponse(Response response) {
        switch (response.statusCode()) {
            case 200:
                System.out.println("Description: Deposit success");
                break;
            case 401:
                System.out.println("Description: Error: Authorization failed.");
                validateErrorResponse(response);
                break;
            case 404:
                System.out.println("Description: Error: Not Found");
                validateNotFoundResponse(response);
                break;
            default:
                System.out.println("Unhandled status code: " + response.statusCode());
        }
    }

    private void validateErrorResponse(Response response) {
        if (response.jsonPath().get("err") != null && response.jsonPath().get("err.message") != null) {
            assertThat(response.jsonPath().get("err.message"), instanceOf(String.class));
        }
    }

    private void validateNotFoundResponse(Response response) {
        if (response.jsonPath().get("err") != null && response.jsonPath().get("err.message") != null) {
            assertThat(response.jsonPath().get("err.message"), instanceOf(String.class));
        }
    }
}
