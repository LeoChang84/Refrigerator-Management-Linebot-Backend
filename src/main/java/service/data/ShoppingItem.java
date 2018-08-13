package service.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.Boolean;

public class ShoppingItem {

	@Id
	private String id;
    private String nameZh;
    private String type;

    public ShoppingItem() { }

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