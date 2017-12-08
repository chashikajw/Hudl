package hudlmo.interfaces.History;

/**
 * Created by Shalini PC on 12/6/2017.
 */

public class HistoryClass {
        String key;
        String duration;
        String convoName;
        String date;
        String startTime;
        //private String initiator;
        String description;
        //String[] participants;

        public HistoryClass() {
        }
        public HistoryClass(String key,String convoName,String date,String startTime,String description,String duration){
            this.key = key ;
            this.convoName = convoName;
            this.date = date;
            this.startTime = startTime;
            this.duration = duration;
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



        //Duration of Concersation
        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
        this.duration = duration;
    }

}
