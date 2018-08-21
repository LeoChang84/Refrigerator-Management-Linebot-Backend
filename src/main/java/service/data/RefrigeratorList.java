package service.data;

import org.springframework.data.annotation.Id;
import java.util.List;

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