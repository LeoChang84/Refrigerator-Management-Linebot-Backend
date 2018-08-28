package service.data;

import org.springframework.data.annotation.Id;

public class ShoppingItem {

	@Id
	private String id;
    private String nameZh;
    private String type;

    public ShoppingItem() { }

    public ShoppingItem(String nameZh) {
        this.nameZh = nameZh;
    }

	public ShoppingItem (String nameZh, String type) {
		this.nameZh = nameZh;
		this.type = type;
	}

    public ShoppingItem (String id, String nameZh, String type) {
        this.id = id;
        this.nameZh = nameZh;
        this.type = type;
    }
    public String getId() {
        return id;
    }

    public String getNameZh() {
    	return nameZh;
    }

    public String getType() {
    	return type;
    }

    public String toString() {
        return "[" + nameZh + " " + type + "]";
    }
}