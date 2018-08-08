package service.model;

import service.model.Food;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "shopping_list")
public class Food {

	@Id
	private String id;
    private String nameZh;
    private String type;

	public Food(String nameZh, String type) {
		this.nameZh = nameZh;
		this.type = type;
	}
	
    public String getNameZh() {
    	return nameZh;
    }
}