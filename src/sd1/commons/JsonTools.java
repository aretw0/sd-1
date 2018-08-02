package sd1.commons;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JsonTools {
	static final Gson gsonExpose = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	static final Gson gson = new Gson();
	
	static List<Product> PLFromJson(String json) {
		return JsonTools.gsonExpose.fromJson(json, new TypeToken<List<Product>>(){}.getType());
	}
}
