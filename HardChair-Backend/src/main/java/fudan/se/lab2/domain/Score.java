package fudan.se.lab2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "Score")
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Long pcmember_id;

    @Column
    private String score;

    @Column
    private String comment;

    @Column
    private String confidence;

    @Column
    private String reviewStatus; //是否有分数

    @Column
    private String modifyStatus; //是否确认或修改过分数

    @ManyToOne
    @JoinColumn(name = "article_id", referencedColumnName = "id")
    @JsonIgnore
    private Article article2;

    @Column
    private String oldId; //第二次修改评分，存储旧的评分及其Id

    public Score() {
    }

    public Score(Long pcmember_id) {
        this.pcmember_id = pcmember_id;
    }

    public Score(Long pcmember_id, String score, String comment, String confidence, String reviewStatus, String oldId) {
        this.pcmember_id = pcmember_id;
        this.score = score;
        this.comment = comment;
        this.confidence = confidence;
        this.reviewStatus = reviewStatus;
        this.oldId = oldId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPcmember_id() {
        return pcmember_id;
    }

    public void setPcmember_id(Long pcmember_id) {
        this.pcmember_id = pcmember_id;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    public Article getArticle2() {
        return article2;
    }

    public void setArticle2(Article article2) {
        this.article2 = article2;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public String getModifyStatus() {
        return modifyStatus;
    }

    public void setModifyStatus(String modifyStatus) {
        this.modifyStatus = modifyStatus;
    }

    public String getOldId() {
        return oldId;
    }

    public void setOldId(String oldId) {
        this.oldId = oldId;
    }
}
