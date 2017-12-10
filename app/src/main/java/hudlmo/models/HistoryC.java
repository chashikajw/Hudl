package hudlmo.models;

import java.io.Serializable;

/**
 * Created by Shalini PC on 12/8/2017.
 */

public class HistoryC implements Serializable {
    String key;
   // String participants;
    String initiator;
    String convoName;
    String date;
    String startTime;
    //private String initiator;
    String description;
    //String[] participants;

    public HistoryC() {
    }
    public HistoryC(String key,String convoName,String date,String startTime,String description,String initiator){
        this.key = key ;
        this.convoName = convoName;
        this.date = date;
        this.startTime = startTime;
        this.initiator = initiator;
        this.description = description;
    }

    //ID
    public String get_Key() {
        return key;
    }

    public void set_Key(String key) {
        this.key = key;
    }


    //Conversation Name
    public String getConvoName() {
        return convoName;
    }

    public void setConvoName(String convoName) {
        this.convoName = convoName;
    }



    //Conversation Date
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    //Conversation Start Time
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }


    //Conversation Description
    public String getDescription() {
        return description;
    }

    public void setDescription(String initiator) {
        this.description = description;
    }



    //Duration of Conversation
    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

}
