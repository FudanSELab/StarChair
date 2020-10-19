package fudan.se.lab2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "Topic")
public class Topic {
    @Id //主键
    @GeneratedValue(strategy = GenerationType.AUTO) //设置主键自增
    private Long id;

    @Column
    private String topic;

    @ManyToOne
    @JoinColumn(name = "article_id", referencedColumnName = "id")
    @JsonIgnore
    private Article article;

    public Topic(){}

    public Topic(String topic) {
        this.topic = topic;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
