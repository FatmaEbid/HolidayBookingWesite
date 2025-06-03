package DataProviders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JasonDataReader {


	public static List<Map<String, Object>> getTestData(String filePath) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(new File(filePath), new TypeReference<List<Map<String, Object>>>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}


}
