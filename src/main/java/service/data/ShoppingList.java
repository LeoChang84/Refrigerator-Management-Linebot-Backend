package service.data;

import org.springframework.data.annotation.Id;
import java.util.List;

public class ShoppingList {

	@Id
	private String id;
    private List<ShoppingItem> shoppingItems; 

    public ShoppingList () { }

	public ShoppingList (List<ShoppingItem> shoppingItems) {
		this.shoppingItems = shoppingItems;
	}
    
    public List<ShoppingItem>  getShoppingItems() {
    	return shoppingItems;
    }
}