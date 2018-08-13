package service.data;

import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.Boolean;

public class AddItemToShoppingList {

    private String nameZh;

    public AddItemToShoppingList() { }

	public AddItemToShoppingList (String nameZh) {
		this.nameZh = nameZh;
	}
	
    public String getNameZh() {
    	return nameZh;
    }

    public String toString() {
        return "[" + nameZh +  "]";
    }
}