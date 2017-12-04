package layout;

/**
 * Created by admin on 12/3/2017.
 */

public class Meeting {

    private String groupname;
    private String groupdtails;

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public void setGroupdtails(String groupdtails) {
        this.groupdtails = groupdtails;
    }

    public String getGroupdtails() {
        return groupdtails;
    }

    public Meeting(String groupname, String groupdtails) {
        this.groupname = groupname;

        this.groupdtails = groupdtails;
    }
}
