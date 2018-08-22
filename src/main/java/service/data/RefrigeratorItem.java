package service.data;

import org.springframework.data.annotation.Id;
import java.lang.Boolean;

public class RefrigeratorItem {

	@Id
	private String id;
    private String nameZh;
    private String type;
    private String acquisitionDate;
    private String expirationDate;
    private Boolean notify;
    private Boolean firstUse;
    private Boolean easyExpired;

    public RefrigeratorItem () {}

	public RefrigeratorItem (String id, String nameZh, String type, String acquisitionDate, String expirationDate, Boolean notify, Boolean firstUse, Boolean easyExpired) {
		this.id = id;
        this.nameZh = nameZh;
		this.type = type;
        this.acquisitionDate = acquisitionDate;
        this.expirationDate = expirationDate;
        this.notify = notify;
        this.firstUse = firstUse;
        this.easyExpired = easyExpired;
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

    public Boolean getEasyExpired() { return easyExpired; }

    public Boolean getFirstUse() { return firstUse; }

    public String toString() {
        return "[" + id + " " + nameZh + " " + type + " " + acquisitionDate + " "  + expirationDate + " " + notify + "]";
    }
}