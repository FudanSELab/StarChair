package fudan.se.lab2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "Author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //设置主键自增
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String company;

    @Column
    private String area;

    @Column
    private int orderOfAuthor;

    @ManyToOne
    @JoinColumn(name = "article_id", referencedColumnName = "id")
    @JsonIgnore
    private Article article1;

    public Author() {
    }

    public Author(String name, String email, String company, String area) {
        this.name = name;
        this.email = email;
        this.company = company;
        this.area = area;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Article getArticle1() {
        return article1;
    }

    public void setArticle1(Article article1) {
        this.article1 = article1;
    }

    public int getOrderOfAuthor() {
        return orderOfAuthor;
    }

    public void setOrderOfAuthor(int orderOfAuthor) {
        this.orderOfAuthor = orderOfAuthor;
    }
}
