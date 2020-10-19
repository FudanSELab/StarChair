package fudan.se.lab2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "topic_pc_meeting_article")
public class TopicPcMeetingArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "topic_id", referencedColumnName = "id")
    @JsonIgnore
    private Topic topic;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private User pcmember;

    @ManyToOne
    @JoinColumn(name = "meeting_id", referencedColumnName = "id")
    @JsonIgnore
    private Meeting meeting;

    public TopicPcMeetingArticle(){}

    public TopicPcMeetingArticle(Topic topic, User pcmember, Meeting meeting) {
        this.topic = topic;
        this.pcmember = pcmember;
        this.meeting = meeting;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public User getPcmember() {
        return pcmember;
    }

    public void setPcmember(User pcmember) {
        this.pcmember = pcmember;
    }

    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }
}
