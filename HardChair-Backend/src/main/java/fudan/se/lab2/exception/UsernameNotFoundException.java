package fudan.se.lab2.exception;

public class UsernameNotFoundException  extends RuntimeException {
    private static final long serialVersionUID = -6074753940710869977L;

    public  UsernameNotFoundException(String username) {
        super("Username '" + username + "' not found");
    }
}
