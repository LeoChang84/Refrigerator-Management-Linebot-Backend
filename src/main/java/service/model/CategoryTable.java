package service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "categorytable")
public class CategoryTable {

	@Id
	private String id;
    private String nameZh;
    private String type;

	public CategoryTable (String nameZh, String type) {
		this.nameZh = nameZh;
		this.type = type;
	}

    public CategoryTable() { }
	
    public String getId() {
        return id;
    }

    public String getNameZh() {
    	return nameZh;
    }

    public String getType() {
    	return type;
    }

    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toString() {
        return "[" + id + " " + nameZh + " " + type +  "]";
    }
}