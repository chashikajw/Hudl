package hudlmo.models;

import java.io.Serializable;
/**
 * Created by chashika on 7/26/17.
 */

public class Contac implements Serializable {
    private String name;
    private String email;
    private int imageID;

    public Contac(String name, int imageID) {
        this.name = name;
        this.imageID = imageID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }
}
