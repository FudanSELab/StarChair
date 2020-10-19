package fudan.se.lab2.exception;

public class ScoreNotConfirmedException extends RuntimeException {
    public ScoreNotConfirmedException() {
        super("Some Pc haven't confirmed the scores!");
    }
}
