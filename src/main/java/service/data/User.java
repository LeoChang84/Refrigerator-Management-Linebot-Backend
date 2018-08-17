package service.data;

import org.springframework.data.annotation.Id;

public class User {
    @Id
    private String id;
    private String uid;
    public User(String uid) {
        this.uid = uid;
    }
    public User() { }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String toString() {
        return "[" + uid + "]";
    }
}
