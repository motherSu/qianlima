package qianlima.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	
	public static String json(Object obj) {
		// TODO Auto-generated method stub
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String json = objectMapper.writeValueAsString(obj);
			//System.out.println(json);
			return json;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "";
		
	}
	
}
