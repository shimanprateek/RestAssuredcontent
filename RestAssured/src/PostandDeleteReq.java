import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class PostandDeleteReq {
	
	@Test
	public void Integrate()
	{
		
			//BaseURL
					RestAssured.baseURI="http://216.10.245.166";
					String Bodystring ="{" +
						    "\"location\": {"+
						    "\"lat\": -38.383494,"+
						    "\"lng\": 33.427362"+
						    "},"+
							"\"accuracy\": 50,"+
							"\"name\": \"Frontline house\","+
							"\"phone_number\": \"(+91) 983 893 3937\","+
							"\"address\": \"29, side layout, cohen 09\","+
							"\"types\": ["+
							"\"shoe park\","+
							"\"shop\""+
						    "],"+
						   "\"website\": \"http://google.com\","+
						   "\"language\": \"French-IN\""+
						"}";
					
					//Given Block -- Request Headers,Parameters,Cookies,body
					Response res = given().
					queryParam("key"," qaclick123").
					body(Bodystring).
					
		when().			
		post("/maps/api/place/add/json"). 
		
		then().
		assertThat().
	    statusCode(200).and(). 
	    body("status",equalTo("OK")).		
		
					//Extract Block -- YOu can pull out the body of Response
					extract().response();
					String responsebody = res.asString();
					System.out.println("Response for Posting : " + res.asString() + "\n");
					
					JsonPath jsonevaluator =new JsonPath(responsebody);
					String getPlace_id =jsonevaluator.get("place_id");
					System.out.println("Place_id : " + getPlace_id + "\n");
					
	
					// Another Request for Deletion
					
					Response res1 = given().
					queryParam("key"," qaclick123").
                    body("{\"place_id\":\""+getPlace_id+"\"}").
                    when().
                    post("/maps/api/place/delete/json").
                    then().
                    assertThat().
                    statusCode(200).and(). 
            	    body("status",equalTo("OK")).
            	    extract().response();
					
					System.out.println("Response for Deletion : " + res1.asString() + "\n");
	}
	

}
