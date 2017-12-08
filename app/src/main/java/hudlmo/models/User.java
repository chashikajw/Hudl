package hudlmo.models;

import java.io.Serializable;

/**
 * Created by chashika on 7/26/17.
 */

public class User implements Serializable {
    private String name;
    private String username;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User() {
    }

    public User(String name, String username) {
        this.name = name;
        this.username = username;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
