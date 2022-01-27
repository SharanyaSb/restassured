package Utilties;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;


public class Utilties {
		
	public static RequestSpecification req; // it should be static else even in second run of the requestspecbuilder it will be null. null will be always.
	public RequestSpecification requestspecbuilder() throws IOException {
		
		if(req==null) {
		PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
		 req =new RequestSpecBuilder().setBaseUri(globalvalueproperties("Baseurl"))
				.addQueryParam("key", "qaclick123")
				.addFilter(RequestLoggingFilter.logRequestTo(log))
				.addFilter(ResponseLoggingFilter.logResponseTo(log))
				.setContentType(ContentType.JSON).build();		
		return req;
		}
		return req;
		
	}
	
	
	public ResponseSpecification responsespecbuilder() {
		
		ResponseSpecification  resspec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		return resspec;
	}
	
	public String globalvalueproperties(String key) throws IOException {
		
		Properties p = new Properties();
		FileInputStream fis = new FileInputStream("D:\\software\\cucumber\\RestApiFrmwk\\src\\test\\resources\\Utils\\Global.properties");
		p.load(fis);
		return p.getProperty(key);
	}
	 
	
	public String jsonparse(Response response, String key) {
		String responseString=response.asString();
	    
	    JsonPath js =new JsonPath(responseString);
	    return js.get(key).toString();
		
	}
	
}
