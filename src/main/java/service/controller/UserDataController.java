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



import service.data.User;
import service.data.UserList;
import service.model.UserId;
import service.repository.UserIdRepository;
import service.util.ReadQRCode;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserDataController {

    static private Logger logger = LoggerFactory.getLogger(UserDataController.class.getName());

    @Autowired
    UserIdRepository userIdRepository;

    @GetMapping(value = "/{userId}/get_uid", produces = "application/json")
    public ResponseEntity<UserList> GetUserId(@PathVariable("userId") String userId) {
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
    public ResponseEntity<String> PostUIdtoDB(@RequestBody String uid) throws JsonGenerationException, JsonMappingException, IOException {
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

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");
            // ReadQRCode readQRCode = new ReadQRCode();
            // result = new ReadQRCode().scanQRcode(path);
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
        if (result.length() > 77) {
            String[] reciptList = result.split(":");
            int loop = (reciptList.length - 5) / 3;
            for (int i = 0; i < loop; i++) {
                System.out.println(reciptList[5 + i * 3]);
            }
            System.out.println(reciptList.length);
        } else if (result.length() > 0) {
            String[] reciptList = result.split(":");
            int loop = (reciptList.length - 1) / 3;
            for (int i = 0; i < loop; i++) {
                System.out.println(reciptList[1 + i * 3]);
            }
            System.out.println(reciptList.length);
        } else {

        }
        reply = "QR code be parsed successfully";
        return new ResponseEntity<>(reply, HttpStatus.OK);
    }

}
