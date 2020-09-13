import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Based on:
 * 
 * http://www.mkyong.com/java/how-to-convert-java-map-to-from-json-jackson/
 */
public class JsonLoader {
    private static final ObjectMapper OBJ_MAPPER;
    private static final TypeReference<Map<String,Object>> OBJ_MAP;
    private static final TypeReference<List<Map<String,Object>>> OBJ_LIST;

    static {
        OBJ_MAPPER = new ObjectMapper();
        OBJ_MAP = new TypeReference<Map<String,Object>>(){};
        OBJ_LIST = new TypeReference<List<Map<String,Object>>>(){};
    }

    public static void main(String[] args) {
        try {
            System.out.println(jsonToString(parseJsonString(read("data_map.json", true))));
            System.out.println(jsonToString(parseJsonString(read("data_array.json", true))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final Object parseJsonString(String jsonString) {
        try {
            if (jsonString.startsWith("{")) {
                return readJsonObject(jsonString);
            } else if (jsonString.startsWith("[")) {
                return readJsonArray(jsonString);
            }
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String jsonToString(Object json) throws JsonProcessingException {
        return OBJ_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(json);
    }

    private static final Map<String,Object> readJsonObject(String jsonObjectString) throws JsonParseException, JsonMappingException, IOException {
        return OBJ_MAPPER.readValue(jsonObjectString, OBJ_MAP);
    }

    private static final List<Map<String,Object>> readJsonArray(String jsonArrayString) throws JsonParseException, JsonMappingException, IOException {
        return OBJ_MAPPER.readValue(jsonArrayString, OBJ_LIST);
    }

    public static final Map<String,Object> loadJsonObject(String path, boolean isResource) throws JsonParseException, JsonMappingException, MalformedURLException, IOException {
        return OBJ_MAPPER.readValue(load(path, isResource), OBJ_MAP);
    }

    public static final List<Map<String,Object>> loadJsonArray(String path, boolean isResource) throws JsonParseException, JsonMappingException, MalformedURLException, IOException {
        return OBJ_MAPPER.readValue(load(path, isResource), OBJ_LIST);
    }

    private static final URL pathToUrl(String path, boolean isResource) throws MalformedURLException {
        if (isResource) {
            return JsonLoader.class.getClassLoader().getResource(path);
        }

        return new URL("file:/" + path);
    }

    protected static File load(String path, boolean isResource) throws MalformedURLException {
        return load(pathToUrl(path, isResource));
    }

    protected static File load(URL url) {
        try {
            return new File(url.toURI());
        } catch (URISyntaxException e) {
            return new File(url.getPath());
        }
    }

    public static String read(String path, boolean isResource) throws IOException {
        return read(path, "UTF-8", isResource);
    }

    public static String read(String path, String charset, boolean isResource) throws IOException {
        return read(pathToUrl(path, isResource), charset);
    }

    @SuppressWarnings("resource")
    public static String read(URL url, String charset) throws IOException {
        return new Scanner(url.openStream(), charset).useDelimiter("\\A").next();
    }
}