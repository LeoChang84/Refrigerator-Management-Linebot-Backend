package service.data;

import service.data.ShoppingItem;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.lang.Boolean;

public class ShoppingList {

	@Id
	private String id;
    private List<ShoppingItem> shoppingItems; 

	public ShoppingList (List<ShoppingItem> shoppingItems) {
		this.shoppingItems = shoppingItems;
	}
    
    public List<ShoppingItem>  getShoppingItems() {
    	return shoppingItems;
    }
}