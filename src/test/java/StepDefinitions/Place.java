package StepDefinitions;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.google.gson.JsonObject;

import Utilties.DataBuilder;
import Utilties.APIResources;
import Utilties.Utilties;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Place extends Utilties {

	RequestSpecification res;
	Response response;
	static String place_id;
	DataBuilder data = new DataBuilder();

	@Given("Add Place Payload with {string} {string} {string}")
	public void add_place_payload_with(String name, String Lang, String address) throws IOException {
		System.out.println("Execution started....");
		res = given().spec(requestspecbuilder()).body(data.addplace(name, Lang, address));
	}

	@When("user calls {string} with {string} Http request")
	public void user_calls_with_post_http_request(String resour, String method) {
		APIResources resource = APIResources.valueOf(resour);
		System.out.println(resource.getResource());
		if (method.equalsIgnoreCase("Post")) {
			response = res.when().post(resource.getResource());
		} else if (method.equalsIgnoreCase("Get")) {
			response = res.when().get(resource.getResource());
		}
	}

	@Then("the API call got success with status code {int}")
	public void the_api_call_got_success_with_status_code(int int1) {

		assertEquals(response.getStatusCode(), int1);

	}

	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyvalue, String expectedvalue) {

		assertEquals(jsonparse(response, keyvalue), expectedvalue);
	}

	@Then("verify {string} is created and {string} is matching in the response using {string}")
	public String verify_placeid_is_created_and_is_matching_in_the_response_using_http_request(String place,
			String name, String resource) throws IOException {

		place_id = jsonparse(response, place);
		res = given().spec(requestspecbuilder()).queryParam(place, place_id);
		user_calls_with_post_http_request(resource, "Get");
		assertEquals(jsonparse(response, "name"), name);
		return place_id;

	}

	@Given("Delete place payload")
	public void delete_place_payload() throws IOException {
		// System.out.println(place_id);
		res = given().spec(requestspecbuilder()).body(data.deletepayload(place_id));

	}

	@Given("LibraryAPI payload")
	public void library_api_payload() throws IOException {

		ArrayList<String> li = data.getData("Validate LibraryAPI by converting Json to Excel", "PlaceValidation");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("name", li.get(1));
		map.put("isbn", li.get(2));
		map.put("aisle", li.get(3));
		map.put("author", li.get(4));
		//
		res = given().spec(requestspecbuilder()).body(map);
		response = res.when().post("/Library/Addbook.php").then().extract().response();
	}

	@Given("Addplace payload")
	public void addplace_payload() throws IOException {

		//ArrayList<String> li = data.getData("Validate Addplace by converting Json to Excel", "PlaceValidation");
		HashMap<String, Object> map = new HashMap<String, Object>();
		HashMap<String, Object> map2 = new HashMap<>();

		ArrayList<String> ar = new ArrayList<String>();
		ar.add("shoe park");
		ar.add("shop");

		map.put("name", "Frontline ");
		map2.put("lat", "-38.383494");
		map2.put("lng", "33.427362");
		map.put("accuracy", "50");
		map.put("phone_number", "(+91) 983 893 3937");
		map.put("address", "29, side layout, cohen 09");
		map.put("types", ar);
		map.put("website", "http://google.com");
		map.put("language", "French-IN");

		map.put("location", map2);

		/*
		 * HashMap<String, String> map3 = new HashMap<String, String>(); for (int i = 0;
		 * i < ar. ; i++) { JsonObject j = k.optJSONObject(i); Iterator it = j.keys();
		 * while (it.hasNext()) { String n = it.next(); pairs.put(n, j.getString(n)); }
		 * }
		 */

		res = given().spec(requestspecbuilder()).body(map);
		response = res.when().post("/maps/api/place/add/json").then().extract().response();
	}

	/*
	 * ArrayList<String> li
	 * =data.getData("Validate Addplace by converting Json to Excel",
	 * "PlaceValidation"); HashMap<String, Object> map = new HashMap<String,
	 * Object>(); HashMap<String, Object> map2 = new HashMap<>();
	 * 
	 * ArrayList<String> ar = new ArrayList<String>(); ar.add("shoe park");
	 * ar.add("shop");
	 * 
	 * map.put("name", li.get(1)); map2.put("lat", li.get(6)); map2.put("lng",
	 * li.get(7)); map.put("accuracy", li.get(8)); map.put("phone_number",
	 * li.get(9)); map.put("address", li.get(10)); map.put("types", ar);
	 * map.put("website", li.get(12)); map.put("language", li.get(13));
	 * 
	 * map.put("location", map2);
	 * 
	 * 
	 * HashMap<String, String> map3 = new HashMap<String, String>(); for (int i = 0;
	 * i < ar. ; i++) { JsonObject j = k.optJSONObject(i); Iterator it = j.keys();
	 * while (it.hasNext()) { String n = it.next(); pairs.put(n, j.getString(n)); }
	 * }
	 * 
	 * 
	 * res=given().spec(requestspecbuilder()).body(map);
	 * response=res.when().post("/maps/api/place/add/json").then().extract().
	 * response();
	 */

}
