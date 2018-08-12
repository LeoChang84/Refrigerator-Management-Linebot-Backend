package service.data;

import service.data.RefrigeratorItem;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;  
import java.lang.Boolean;

public class RefrigeratorList {

	@Id
	private String id;
    private List<RefrigeratorItem> refrigeratorList;

	public RefrigeratorList (List<RefrigeratorItem> refrigeratorList) {
		this.refrigeratorList = refrigeratorList;
	}
	
    public List<RefrigeratorItem> getRefrigeratorList() {
    	return refrigeratorList;
    }

    public String toString() {
        return "[" + refrigeratorList + "]";
    }
}