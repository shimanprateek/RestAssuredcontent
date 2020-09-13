import static io.restassured.RestAssured.given;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PostRequest {
	
	@Test
	public void postRequesttest()
	{
		//BaseURL
				RestAssured.baseURI="http://216.10.245.166";
				
				//Given Block -- Request Headers,Parameters,Cookies,body
				given().
				queryParam("key"," qaclick123").
				body("{" +
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
"}").
				
	when().			
	post("/maps/api/place/add/json"). 
	
	then().
	assertThat().
    statusCode(200).and(). 
    body("status",equalTo("OK"));			
	}
	
	public static String GenerateStringfromsources(Path path) throws IOException 
	{
		return new String(Files.readAllBytes(path));
	}
	
	public XmlPath RawToXML(Response res)
	{
		String responsebody = res.asString();
		XmlPath xmlevaluator =new XmlPath(responsebody);
		return xmlevaluator;
	}
	
	public JsonPath RawToJson(Response res)
	{
		String responsebody = res.asString();
		JsonPath jsonevaluator =new JsonPath(responsebody);
		return jsonevaluator;
	}

}
