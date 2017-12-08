package hudlmo.interfaces.registerPage;

/**
 * Created by Piyumi on 12/6/2017.
 */

public class UserDetails {

    public String name;
    public String uname;
    public String email;
    public String password;
    public String comfirm_pw;

    public UserDetails(String name, String uname, String email, String password, String comfirm_pw) {
        this.name = name;
        this.uname = uname;
        this.email = email;
        this.password = password;
        this.comfirm_pw = comfirm_pw;
    }



    public UserDetails()
    {}

    public String getName() {
        return name;
    }

    public String getUname() {
        return uname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getComfirm_pw() {
        return comfirm_pw;
    }

    public void setName(String name) {
        this.name = name;
    }
}
