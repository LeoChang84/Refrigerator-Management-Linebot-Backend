package service.model;

import service.model.Food;

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
    private Boolean eaten;
    private Boolean notify;


	public Food (String nameZh, String type, String acquisitionDate, String expirationDate, Integer status, Boolean eaten, Boolean notify) {
		this.nameZh = nameZh;
		this.type = type;
		this.acquisitionDate = acquisitionDate;
    	this.expirationDate = expirationDate;
    	this.status = status;
    	this.eaten = eaten;
    	this.notify =notify;
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

    public Boolean getEaten() {
    	return eaten;
    }

    public Boolean getNotify() {
    	return notify;
    }
}