package fudan.se.lab2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

/**
 * @author LBW
 */
@Entity //实体类
@Table(name = "User")
public class User implements UserDetails {

    private static final long serialVersionUID = -6140085056226164016L;

    @Id //主键
    @GeneratedValue(strategy = GenerationType.AUTO) //设置主键自增
    private Long id; //用户的唯一标识

    @Column(unique = true) //映射为字段，用户名唯一，值不能为空
    private String username;

    @Column
    private String fullName;

    @Column
    private String password;

    @Column
    private String e_mail;

    @Column
    private String companie;

    @Column
    private String area;

    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER, mappedBy = "user")
    private Set<Authority> authorities = new HashSet<>();

    @ManyToMany(mappedBy = "pcmembers") //被关联表
    @JsonIgnore
    private Set<Article> articles = new HashSet<>();

    public User() {
    }

    public User(String username, String fullName, String password, String e_mail, String companie, String area, Set<Authority> authorities) {
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.authorities = authorities;
        this.e_mail = e_mail;
        this.companie = companie;
        this.area = area;
    }

    @Override
    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public String getCompanie() {
        return companie;
    }

    public void setCompanie(String companie) {
        this.companie = companie;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }
}
