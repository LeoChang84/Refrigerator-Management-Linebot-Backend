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
import service.data.User;
import service.data.UserList;
import service.model.UserId;
import service.repository.UserIdRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserDataController {

    static private Logger logger = LoggerFactory.getLogger(CabinetController.class.getName());

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


}
