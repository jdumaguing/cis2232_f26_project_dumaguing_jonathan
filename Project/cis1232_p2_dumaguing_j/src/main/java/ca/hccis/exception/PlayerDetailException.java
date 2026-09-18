package ca.hccis.exception;

public class PlayerDetailException extends Exception {

    public PlayerDetailException() {
        super("Player Detail Exception");
    }
    public PlayerDetailException(String message) {
        super("Player Detail Exception: " + message);
    }
}
