import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class JavaParsing {

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
	
	public static  void getKeyValue( Object MAPInList)
	{
		LinkedHashMap<String,Object> map = (LinkedHashMap<String, Object>) MAPInList;
		for (Entry<String, Object> entry : map.entrySet())
		{
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}	
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Path p = Paths.get("C:/Users/shima/workspace/RestAssured/file.json");
		String response_string = GenerateStringfromsources(p);
		
		JsonPath js = new JsonPath(response_string);
		//System.out.println(js.get("Countries[0].Data.Capital").toString());
		
		/*List<String> ls =js.getList("Countries[*].Data.Capital");
		System.out.println(ls);*/
		int count = js.get("Countries.Data.size()");
		System.out.println("Count of Data :  " + count);
		List ls =js.getList("Countries.Data");
		System.out.println(ls);
		Iterator it  = ls.iterator();
		while (it.hasNext())
		{
			getKeyValue(it.next());
			System.out.println("================================");
		}
		
		
//		LinkedHashMap<String,Object> hsmap = (LinkedHashMap<String, Object>) ls.get(0); 
//		for (Entry<String, Object> entry : hsmap.entrySet())
//		{
//			System.out.println(entry.getKey() + ":" + entry.getValue());
//		}
//		
		
		
		//System.out.println(ls.get(0));
		
		
		
		//List <String> ls = js.getList("Countries[*].Data");
		//List <Data> data = js.getList("Countries[?(@.Data)]", Data.class);
		//System.out.println(data);
		//System.out.println(ls);
		
	}
		

}
