package service.controller;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import service.data.AddItemToShoppingList;
import service.data.User;
import service.data.UserList;
import service.model.*;
import service.repository.*;
import service.util.ReadQRCode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
@RequestMapping(value = "/user")
public class UserDataController {

    static private Logger logger = LoggerFactory.getLogger(UserDataController.class.getName());

    private static final int BUFFER_SIZE = 4096;

    @Autowired
    CabinetController cabinetController;
    @Autowired
    UserIdRepository userIdRepository;
    @Autowired
    CategoryTableRepository categoryTableRepository;
    @Autowired
    ExpirationRepository expirationRepository;
    @Autowired
    EasyExpiredrepository easyExpiredrepository;
    @Autowired
    CabinetRepository cabinetRepository;

    @GetMapping(value = "/{userId}/get_uid", produces = "application/json")
    public ResponseEntity<UserList> getUserId(@PathVariable("userId") String userId) {
        logger.info("Start to get User List");
        List<UserId> UIds  = userIdRepository.findAll();
        if (null == UIds || UIds.isEmpty()) {
            logger.info("UserList got nothing");
            UserList userList = new UserList(new ArrayList<>());
            return new ResponseEntity<>(userList, HttpStatus.OK);
        }
        List<User> users = new ArrayList<>();
        for (UserId uid: UIds) {
            User user = new User(uid.getUid());
            users.add(user);
        }
        logger.info("Successfully transform UId info to userId");
        UserList userList = new UserList(users);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
    @PostMapping(value = "/{userId}/post_uid", produces = "application/json")
    public ResponseEntity<String> postUIdtoDB(@RequestBody String uid) throws JsonGenerationException, JsonMappingException, IOException {
        logger.info("Start to Post UID to DB");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        ObjectMapper objectmapper = new ObjectMapper();
        User user = objectmapper.readValue(uid, User.class);
        logger.info("Parse input object successfully: " + user);

        UserId userId = userIdRepository.save(new UserId(user.getUid()));
        logger.info("Post from db successfully.");
        String reply = "Post uid to UserList." ;
        return new ResponseEntity<>(reply, HttpStatus.OK);
    }

    @PostMapping("/upload") 
    public ResponseEntity<String> singleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        String reply;
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            reply = "File is Empty";
            return new ResponseEntity<>(reply, HttpStatus.OK);
        }

        String result = "";
        try {
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get("./src/main/resources/picture/" + file.getOriginalFilename());
            System.out.println("upload successfully: " + path);
            Files.write(path, bytes);

            redirectAttributes.addFlashAttribute("message", "You successfully uploaded '" + file.getOriginalFilename() + "'");

            try {
                result = new ReadQRCode().scanQRcode(String.valueOf(path));
            } catch (Exception e) {
                logger.info("Error be caugth");
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        LocalDate now = LocalDate.now();
        if (result.length() > 77) {
            String[] receiptList = result.split(":");
            int loop = ((receiptList.length - 5) / 3) + 1;
            for (int i = 0; i < loop; i++) {
                CategoryTable categoryTable = categoryTableRepository.findOneByNameZh(receiptList[5 + i * 3]);
                ExpirationDoc expirationDoc = expirationRepository.findByNameZh(receiptList[5 + i * 3]);
                EasyExpired easyExpired = easyExpiredrepository.findOneByType(categoryTable.getType());
                String expiration = "0";
                if (expirationDoc != null) {
                    expiration = expirationDoc.getExpirationDate();
                }
                Boolean flag = Boolean.FALSE;
                if (easyExpired != null) { flag = Boolean.TRUE; }

                Food test = new Food(categoryTable.getNameZh(), categoryTable.getType(), String.valueOf(now), expiration, 3, null, Boolean.TRUE, Boolean.TRUE , flag);
                logger.info(test.getNameZh() + test.getType() + test.getAcquisitionDate() + test.getExpirationDate() + test.getStatus().toString() );
                Food food = cabinetRepository.save(new Food(categoryTable.getNameZh(), categoryTable.getType(), String.valueOf(now), expiration, 3, null, Boolean.TRUE, Boolean.TRUE , flag));
                logger.info(categoryTable.getNameZh(), categoryTable.getType(), String.valueOf(now), expiration, 3, null, Boolean.TRUE, Boolean.TRUE , flag);

                System.out.println(receiptList[5 + i * 3]);
            }
            System.out.println(receiptList.length);
        } else if (result.length() > 0) {
            String[] receiptList = result.split(":");
            int loop = ((receiptList.length - 1) / 3) + 1;
            for (int i = 0; i < loop; i++) {
                CategoryTable categoryTable = categoryTableRepository.findOneByNameZh(receiptList[1 + i * 3]);
                ExpirationDoc expirationDoc = expirationRepository.findByNameZh(receiptList[1 + i * 3]);
                EasyExpired easyExpired = easyExpiredrepository.findOneByType(categoryTable.getType());
                String expiration = "0";
                if (expirationDoc != null) {
                    expiration = expirationDoc.getExpirationDate();
                }
                Boolean flag = Boolean.FALSE;
                if (easyExpired != null) { flag = Boolean.TRUE; }
                logger.info(categoryTable.getNameZh(), categoryTable.getType(), String.valueOf(now), expiration, 3, null, Boolean.TRUE, Boolean.TRUE , flag);
                Food food = cabinetRepository.save(new Food(categoryTable.getNameZh(), categoryTable.getType(), String.valueOf(now), expiration, 3, null, Boolean.TRUE, Boolean.TRUE , flag));

                System.out.println(receiptList[1 + i * 3]);
            }
            System.out.println(receiptList.length);
        } else {

        }
        reply = "QR code be parsed successfully";
        return new ResponseEntity<>(reply, HttpStatus.OK);
    }

    @GetMapping(value = "/downloadFile/{fileName}") 
    public ResponseEntity<String> downloadFile(@PathVariable("fileName") String fileName) throws IOException {
        String fileURL = "https://i.imgur.com/" + fileName;
        String saveDir = "./src/main/resources/picture/";
        URL url = new URL(fileURL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();
 
        // always check HTTP response code first
        logger.info("try to downloadFile from: " + fileURL);
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // fileName = "";
            String disposition = httpConn.getHeaderField("Content-Disposition");
            String contentType = httpConn.getContentType();
            int contentLength = httpConn.getContentLength();
 
            logger.info("Content-Type = " + contentType);
            logger.info("Content-Disposition = " + disposition);
            logger.info("Content-Length = " + contentLength);
            logger.info("fileName = " + fileName);
 
            // opens input stream from the HTTP connection
            InputStream inputStream = httpConn.getInputStream();
            String saveFilePath = saveDir + File.separator + fileName;
             
            // opens an output stream to save into file
            FileOutputStream outputStream = new FileOutputStream(saveFilePath);
 
            int bytesRead = -1;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
 
            outputStream.close();
            inputStream.close();
 
            logger.info("File downloaded");
        } else {
            logger.info("No file to download. Server replied HTTP code: " + responseCode);
        }
        httpConn.disconnect();

        logger.info(saveDir + fileName);
        addQRcodeTodb(saveDir + fileName);

        String reply = "save file successfully";
        return new ResponseEntity<>(reply, HttpStatus.OK);
    }

    public void addQRcodeTodb(String path) {
        String result = "";
        try {
            result = new ReadQRCode().scanQRcode(String.valueOf(path));
            logger.info(result);
        } catch (Exception e) {
            logger.info("Error be caugth");
            e.printStackTrace() ;
        }
        LocalDate now = LocalDate.now();
        if (result.length() > 77) {
            logger.info("result len: " + result.length());
            String[] receiptList = result.split(":");
            logger.info("receiptList.length : " + String.valueOf(receiptList.length));
            int loop = ((receiptList.length - 5) / 3) + 1;
            logger.info(String.valueOf(loop));
            for (int i = 0; i < loop; i++) {
                logger.info(receiptList[5 + i * 3]);
                CategoryTable categoryTable = categoryTableRepository.findOneByNameZh(receiptList[5 + i * 3]);
                ExpirationDoc expirationDoc = expirationRepository.findByNameZh(receiptList[5 + i * 3]);
                EasyExpired easyExpired = easyExpiredrepository.findOneByType(categoryTable.getType());
                String expiration = "0";
                if (expirationDoc != null) {
                    expiration = expirationDoc.getExpirationDate();
                }
                Boolean flag = Boolean.FALSE;
                if (easyExpired != null) { flag = Boolean.TRUE; }

                Food test = new Food(categoryTable.getNameZh(), categoryTable.getType(), String.valueOf(now), expiration, 3, null, Boolean.TRUE, Boolean.TRUE , flag);
                logger.info(test.getNameZh() + test.getType() + test.getAcquisitionDate() + test.getExpirationDate() + test.getStatus().toString() );
                Food food = cabinetRepository.save(new Food(categoryTable.getNameZh(), categoryTable.getType(), String.valueOf(now), expiration, 3, null, Boolean.TRUE, Boolean.TRUE , flag));
                logger.info(categoryTable.getNameZh(), categoryTable.getType(), String.valueOf(now), expiration, 3, null, Boolean.TRUE, Boolean.TRUE , flag);

                logger.info(receiptList[5 + i * 3]);
            }
            System.out.println(receiptList.length);
        } else if (result.length() > 0) {
            logger.info("result len: " + result.length());
            String[] receiptList = result.split(":");
            int loop = ((receiptList.length - 1) / 3) + 1;
            logger.info(String.valueOf(loop));
            for (int i = 0; i < loop; i++) {
                logger.info((receiptList[1 + i * 3]));
                CategoryTable categoryTable = categoryTableRepository.findOneByNameZh(receiptList[1 + i * 3]);
                ExpirationDoc expirationDoc = expirationRepository.findByNameZh(receiptList[1 + i * 3]);
                EasyExpired easyExpired = easyExpiredrepository.findOneByType(categoryTable.getType());
                String expiration = "0";
                if (expirationDoc != null) {
                    expiration = expirationDoc.getExpirationDate();
                }
                Boolean flag = Boolean.FALSE;
                if (easyExpired != null) { flag = Boolean.TRUE; }
                logger.info(categoryTable.getNameZh(), categoryTable.getType(), String.valueOf(now), expiration, 3, null, Boolean.TRUE, Boolean.TRUE , flag);
                Food food = cabinetRepository.save(new Food(categoryTable.getNameZh(), categoryTable.getType(), String.valueOf(now), expiration, 3, null, Boolean.TRUE, Boolean.TRUE , flag));

                logger.info(receiptList[1 + i * 3]);
            }
            System.out.println(receiptList.length);
        }
    }
}
