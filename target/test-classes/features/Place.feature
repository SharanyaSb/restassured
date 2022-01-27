Feature: PlaceValidation

@AddPlace @Regression @Sharanya
Scenario Outline: Verify if place is being successfully added using AddPlaceAPI
				Given Add Place Payload with "<name>" "<Language>" "<Address>"
				When user calls "AddplaceAPI" with "Post" Http request
				Then the API call got success with status code 200
				And "status" in response body is "OK"
				And "scope" in response body is "APP"
				And verify "place_id" is created and "<name>" is matching in the response using "GetplaceAPI"
				
Examples:
				| name | Language | Address |
				| Cruella | English | World Peace |
			#	| meena | tamil | hello world |
			
@DeletePlace @Regression
Scenario: verify if Delete place functionality is working
				Given Delete place payload
				When user calls "DeleteplaceAPI" with "Post" Http request
				Then the API call got success with status code 200
				And "status" in response body is "OK"

 @Datatable
 Scenario: Validate LibraryAPI by converting Json to Excel
 				Given LibraryAPI payload
 				Then the API call got success with status code 200 
 				
@Complexjson
 Scenario: Validate Addplace by converting Json to Excel
 				Given Addplace payload