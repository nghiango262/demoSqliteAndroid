package info.nghiango.sqlite.db.model;

/**
 * Created by ravi on 20/02/18.
 */

public class User {
    private int id;
    private String user;
    private String pass;
    private String displayName;
    private String type;
    private String description;

    public User() {}

    public User(int id, String user, String pass, String displayName, String type, String description) {
        this.id = id;
        this.user = user;
        this.pass = pass;
        this.displayName = displayName;
        this.type = type;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
