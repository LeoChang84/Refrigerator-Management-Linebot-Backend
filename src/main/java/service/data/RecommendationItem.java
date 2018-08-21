package service.data;

import org.springframework.data.annotation.Id;

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