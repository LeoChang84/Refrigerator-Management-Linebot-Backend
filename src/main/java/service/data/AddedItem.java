package service.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.Boolean;

public class AddedItem {

	@Id
	private String id;
    private String nameZh;
    private String type;
    private String expirationDate;

    public AddedItem() { }

	public AddedItem (String nameZh, String type, String expirationDate) {
		this.nameZh = nameZh;
		this.type = type;
        this.expirationDate = expirationDate;
	}
	
    public String getNameZh() {
    	return nameZh;
    }

    public String getType() {
    	return type;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public String toString() {
        return "[" + nameZh + " " + type + " " + expirationDate + "]";
    }
}