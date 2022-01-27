package StepDefinitions;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {

	Place p = new Place();
	
	@Before("@DeletePlace")
	public void BeforeScenario() throws IOException {
		
		if(Place.place_id==null) {
		p.add_place_payload_with("sharanya", "English", "lansing");
		p.user_calls_with_post_http_request("AddplaceAPI", "Post");
		p.verify_placeid_is_created_and_is_matching_in_the_response_using_http_request("place_id", "sharanya", "GetplaceAPI");

		}
		
		
	}

	
	
	
	
}