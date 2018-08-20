package service;

import service.util.ReadQRCode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
    	System.out.println("---------start---------");
    	ReadQRCode readQRcode = new ReadQRCode();
    	try {
    		readQRcode.scanQRcode();
        } catch(Exception e) {
        	System.out.println(e.getMessage());
        }
        SpringApplication.run(Application.class, args);
    }
}