package fudan.se.lab2.controller.request;

/**
 * @author LBW
 */
public class RegisterRequest {
    private String username;
    private String fullname;
    private String password;
    private String checkPassword;
    private String e_mail;
    private String companie;
    private String area;

    public RegisterRequest(){}

    public RegisterRequest(String username, String fullname, String password, String checkPassword, String e_mail,
                           String companie, String area) {
        this.username = username;
        this.fullname = fullname;
        this.password = password;
        this.checkPassword = checkPassword;
        this.e_mail = e_mail;
        this.companie = companie;
        this.area = area;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getCheckPassword() { return checkPassword; }

    public void setCheckPassword(String checkPassword) { this.checkPassword = checkPassword; }
}

