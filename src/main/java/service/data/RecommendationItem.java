package service.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.Boolean;

public class RecommendationItem {

	@Id
	private String id;
    private String nameZh;
    private String type;
    private Integer quantity;


	public RecommendationItem (String nameZh, String type, Integer quantity) {
		this.nameZh = nameZh;
		this.type = type;
		this.quantity = quantity;
	}
	
    public String getNameZh() {
    	return nameZh;
    }

    public String getType() {
    	return type;
    }

    public Integer getQuantity() {
    	return quantity;
    }
}