package service.data;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.Boolean;
import java.util.function.BinaryOperator;

public class RefrigeratorItem {

	@Id
	private String id;
    private String nameZh;
    private String type;
    private String acquisitionDate;
    private String expirationDate;
    private Boolean notify;

    public RefrigeratorItem () {}

	public RefrigeratorItem (String id, String nameZh, String type, String acquisitionDate, String expirationDate, Boolean notify) {
		this.id = id;
        this.nameZh = nameZh;
		this.type = type;
        this.acquisitionDate = acquisitionDate;
        this.expirationDate = expirationDate;
        this.notify = notify;
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

    public String getAcquisitionDate() {
        return acquisitionDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public Boolean getNotify() { return  notify; }

    public String toString() {
        return "[" + id + " " + nameZh + " " + type + " " + acquisitionDate + " "  + expirationDate + " " + notify + "]";
    }
}