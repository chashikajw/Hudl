package hudlmo.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by initiator on 12/8/2017.
 */

public class Meeting implements Serializable {

    private String meetingName;
    private String description;
    private String createdDate;
    private String sheduleDate;
    private String initiator;
    private String roomId;
    private ArrayList<String> participants;

    public Meeting() {
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

    public String getInitiator() {
        return initiator;
    }

    public String getRoomId() {
        return roomId;
    }

    public ArrayList<String> getParticipants() {
        return participants;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public void setSheduleDate(String sheduleDate) {
        this.sheduleDate = sheduleDate;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setParticipants(ArrayList<String> participants) {
        this.participants = participants;
    }
}
