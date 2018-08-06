package sd1.commons;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class JsonTools {
	public static final Gson gsonExpose = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	public static final Gson gson = new Gson();
	
	public static ObservableList<Product> PLFromJson(String json) {
		ObservableList<Product> list = FXCollections.observableArrayList();
		List<Product> fromJ = JsonTools.gsonExpose.fromJson(json, new TypeToken<List<Product>>(){}.getType());
		for (Product p : fromJ) {
			list.add(p);
		}
		return list;				
	}
}
