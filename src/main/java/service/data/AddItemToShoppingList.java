package service.data;


public class AddItemToShoppingList {

    private String nameZh;

    private String type;

    public AddItemToShoppingList() { }

	public AddItemToShoppingList (String nameZh, String type) {
        this.nameZh = nameZh;
        this.type = type;
	}
    public String getNameZh() {
    	return nameZh;
    }

    public String getType() { return  type; }

    public String toString() {
        return "[" + nameZh + " " + type + "]";
    }
}