package fudan.se.lab2.controller.request;

public class MeetingRequest {
    private String shortenForm;
    private String fullName;
    private String time;
    private String location;
    private String ddl;
    private String publishingTime;
    private String applicant;
    private String[] topic;

    public MeetingRequest(){}

    public MeetingRequest(String fullName, String shortenForm, String time,
                          String location, String ddl, String publishingTime, String applicant,String[] topic) {
        this.fullName = fullName;
        this.shortenForm = shortenForm;
        this.time = time;
        this.location = location;
        this.ddl = ddl;
        this.publishingTime = publishingTime;
        this.applicant = applicant;
        this.topic = topic;
    }

    public String getShortenForm() {
        return shortenForm;
    }

    public void setShortenForm(String shortenForm) {
        this.shortenForm = shortenForm;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDdl() {
        return ddl;
    }

    public void setDdl(String ddl) {
        this.ddl = ddl;
    }

    public String getPublishingTime() {
        return publishingTime;
    }

    public void setPublishingTime(String publishingTime) {
        this.publishingTime = publishingTime;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }


    public String[] getTopic() {
        return topic;
    }

    public void setTopic(String[] topic) {
        this.topic = topic;
    }

}
