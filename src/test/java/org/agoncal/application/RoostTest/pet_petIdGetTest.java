// Test generated by RoostGPT for test testArtillery using AI Type Open AI and AI Model gpt-4


// Test generated for /pet/{petId}_get for http method type GET in rest-assured framework


// RoostTestHash=2c3a7f4837


package org.agoncal.application.RoostTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.hamcrest.MatcherAssert;
import static org.hamcrest.Matchers.*;

public class pet_petIdGetTest {
  
    @Test  
    public void pet_petId_get_Test() {  
        RestAssured.baseURI = System.getenv("BASE_URL");  
  
        // Read CSV file  
        try (BufferedReader reader = new BufferedReader(new FileReader("src/test/java/org/agoncal/application/RoostTest/pet_petId_get.csv"))) {  
            String headerLine = reader.readLine();  
            String[] headers = headerLine.split(",");  
  
            String line;  
            while ((line = reader.readLine()) != null) {  
                String[] data = line.split(",");  
  
                // Create a map of header to data  
                Map<String, String> map = new HashMap<>();  
                for (int i = 0; i < headers.length; i++) {  
                    map.put(headers[i], data[i]);  
                }  
                
  
                Response response = given()
				.pathParam("petId", map.get("petId"))
                .when()
                .get("/pet/{petId}")  
                .then() 
                .extract().response();    
         
                if (response.statusCode() == 200) {
					System.out.println("Description: successful operation");
  
            if (response.jsonPath().get("id") != null) {
                MatcherAssert.assertThat(response.jsonPath().get("id"), instanceOf(Integer.class));
                MatcherAssert.assertThat(response.jsonPath().getString("id"), Number.isSafeInteger(
                  response.jsonPath().getInt("id")
                )); 
  
          }
  
            if (response.jsonPath().get("category") != null) {
                MatcherAssert.assertThat(response.jsonPath().get("category"), instanceOf(undefined.class));  
          }
  
            if (response.jsonPath().get("name") != null) {
                MatcherAssert.assertThat(response.jsonPath().get("name"), instanceOf(String.class));  
          }
  
            if (response.jsonPath().get("photoUrls") != null) {  
                MatcherAssert.assertThat(response.jsonPath().getList("photoUrls"), instanceOf(List.class));
  
          }
  
            if (response.jsonPath().get("tags") != null) {  
              for (int i = 0; i < response.jsonPath().getList("tags").size(); i++) {  
            if (response.jsonPath().get("tags[`"+ i +"`].id") != null) {
                MatcherAssert.assertThat(response.jsonPath().get("tags[`"+ i +"`].id"), instanceOf(Integer.class));
                MatcherAssert.assertThat(response.jsonPath().getString("tags[`"+ i +"`].id"), Number.isSafeInteger(
                  response.jsonPath().getInt("tags[`"+ i +"`].id")
                )); 
  
          }
  
            if (response.jsonPath().get("tags[`"+ i +"`].name") != null) {
                MatcherAssert.assertThat(response.jsonPath().get("tags[`"+ i +"`].name"), instanceOf(String.class));  
          }
  
                }  
                MatcherAssert.assertThat(response.jsonPath().getList("tags"), instanceOf(List.class));
  
          }
  
            if (response.jsonPath().get("status") != null) {
                MatcherAssert.assertThat(response.jsonPath().get("status"), instanceOf(String.class));  
                MatcherAssert.assertThat(response.jsonPath().getString("status"), anyOf(equalTo("available"), equalTo("pending"), equalTo("sold")));
  
          }
				}
if (response.statusCode() == 400) {
					System.out.println("Description: Invalid ID supplied");
				}
if (response.statusCode() == 404) {
					System.out.println("Description: Pet not found");
				}
  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }
}
