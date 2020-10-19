package fudan.se.lab2.exception;

public class AllocationException extends RuntimeException {
    private static final long serialVersionUID = -6074753940710869977L;

    public AllocationException() {
        super("Article allocation error! There must be more than three PcMembers!");
    }
}
