package fudan.se.lab2.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Meeting")
public class Meeting {
    @Id //主键
    @GeneratedValue(strategy = GenerationType.AUTO) //设置主键自增
    private Long id; //会议的唯一标识

    @Column
    private String shortenForm;

    @Column
    private String fullName;

    @Column
    private String time;

    @Column
    private String location;

    @Column
    private String ddl;

    @Column
    private String publishingTime;

    @Column
    private String applicant;

    @Column
    private String isVarified;

    @Column
    private String submitStatus;

    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER, mappedBy = "meeting")
    private Set<Authority> authorities = new HashSet<>();

    public Meeting() {
    }

    public Meeting(String fullName, String shortenForm, String time,
                   String location, String ddl, String publishingTime,
                   String applicant, String isVarified, String submitStatus, Set<Authority> authorities) {
        this.shortenForm = shortenForm;
        this.fullName = fullName;
        this.time = time;
        this.location = location;
        this.ddl = ddl;
        this.publishingTime = publishingTime;
        this.applicant = applicant;
        this.isVarified = isVarified;
        this.submitStatus = submitStatus;
        this.authorities = authorities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getIsVarified() {
        return isVarified;
    }

    public void setIsVarified(String isVarified) {
        this.isVarified = isVarified;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public String getSubmitStatus() {
        return submitStatus;
    }

    public void setSubmitStatus(String submitStatus) {
        this.submitStatus = submitStatus;
    }


}
