package service.data;

import org.springframework.data.annotation.Id;

import java.util.List;

public class UserList {
    @Id
    private String id;
    private List<User> uidlist;

    public UserList(List<User> uidlist) {
        this.uidlist = uidlist;
    }

    public List<User> getUidlist() {
        return uidlist;
    }
}
