package service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.Boolean;

@Document(collection = "cabinet")
public class Food {

	@Id
	private String id;
    private String nameZh;
    private String type;
    private String acquisitionDate;
    private String expirationDate;
    private Integer status; // 1: in shopping. 2: in refrigerator. 3: has been eaten
    private Boolean eatenBeforeExpired;
    private Boolean notify;
    private Boolean firstUse;
    private Boolean easyExpired;


	public Food (String nameZh, String type, String acquisitionDate, String expirationDate, Integer status, Boolean eatenBeforeExpired, Boolean notify, Boolean firstUse, Boolean easyExpired) {
		this.nameZh = nameZh;
		this.type = type;
		this.acquisitionDate = acquisitionDate;
    	this.expirationDate = expirationDate;
    	this.status = status;
    	this.eatenBeforeExpired = eatenBeforeExpired;
    	this.notify = notify;
    	this.firstUse = firstUse;
    	this.easyExpired = easyExpired;
	}

    public Food() { }
	
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

    public Integer getStatus() {
    	return status;
    }

    public Boolean getEatenBeforeExpired() {
    	return eatenBeforeExpired;
    }

    public Boolean getNotify() {
    	return notify;
    }

    public Boolean getFirstUse() { return  firstUse; }

    public Boolean getEasyExpired() { return easyExpired; }

    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAcquisitionDate(String acquisitionDate) {
        this.acquisitionDate = acquisitionDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setEatenBeforeExpired(Boolean eatenBeforeExpired) {
        this.eatenBeforeExpired = eatenBeforeExpired;
    }

    public void setNotify(Boolean notify) {
        this.notify = notify;
    }

    public void setFirstUse(Boolean firstUse) { this.firstUse = firstUse; }

    public void setEasyExpired(Boolean easyExpired) { this.easyExpired = easyExpired; }

    public String toString() {
        return "[" + id + " " + nameZh + " " + type + " " + acquisitionDate + " " + expirationDate + "]";
    }
}