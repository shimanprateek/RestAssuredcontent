import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

public class Basics {

	@Test
	public void Resttest(){
	//public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//BaseURL
		RestAssured.baseURI="https://maps.googleapis.com";
		
		//Given Block -- Request Headers,Parameters,Cookies,body
		given(). 
		       param("location","-33.8670522,151.1957362").
	           param("radius","1500").
		       param("type","restaurant").
		       param("keyword","cruise").
		       param("key","AIzaSyCZi-qKwr_TaCqmh04ggSfx0PqVNZ5wr4Q").
		       
		       //header("","").
			   //cookie("","").
		       //body().
		       
		 //When Block  -- get(resource),post(resource),put(resource),delete(resouce)  etc.
		 when().
		      get("/maps/api/place/nearbysearch/json").
		
		
		//Then Block  -- put assertions and validate response
		then()
		       .assertThat().
		        statusCode(200).and().
		        contentType(ContentType.JSON).and().
	            body("results[0].name",equalTo("Cruise Bar")).and().
		        body("results[0].place_id",equalTo("ChIJi6C1MxquEmsR9-c-3O48ykI")).and().
		        header("Server","scaffolding on HTTPServer2");
		
		
		//Extract Block -- YOu can pull out the body of Response
		
		// Create a place =response (place id)

		// delete Place = (Request - Place id)
	}

}
