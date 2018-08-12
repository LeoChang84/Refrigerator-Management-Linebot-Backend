package service.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.Boolean;

public class RefrigeratorItem {

	@Id
	private String id;
    private String nameZh;
    private String type;
    private String acquisitionDate;
    private String expirationDate;

	public RefrigeratorItem (String nameZh, String type, String acquisitionDate, String expirationDate) {
		this.nameZh = nameZh;
		this.type = type;
        this.acquisitionDate = acquisitionDate;
        this.expirationDate = expirationDate;
	}
	
    public String getNameZh() {
    	return nameZh;
    }

    public String getType() {
    	return type;
    }

    public String getAcquisitionDate() {
        return acquisitionDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public String toString() {
        return "[" + nameZh + " " + type + " " + acquisitionDate + " "  + expirationDate + "]";
    }
}