package hudlmo.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by admin on 12/8/2017.
 */

public class Meeting implements Serializable {

    private String meetingName;
    private String description;
    private String createdDate;
    private String sheduleDate;
    private String admin;
    private String roomId;
    private ArrayList<String> participants;

    public Meeting() {
    }


    public ArrayList<String> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<String> participants) {
        this.participants = participants;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getAdmin() {
        return admin;
    }


    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedDate(String createdDate) {
        createdDate = createdDate;
    }

    public void setSheduleDate(String sheduleDate) {
        sheduleDate = sheduleDate;
    }


    public String getMeetingName() {
        return meetingName;
    }

    public String getDescription() {
        return description;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getSheduleDate() {
        return sheduleDate;
    }


}
