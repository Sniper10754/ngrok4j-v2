package org.ngrok4j;

public class NgrokException extends RuntimeException {
    public NgrokException(String message, Exception e) {
        super(message, e);
    }

    public NgrokException(String message) {
        super(message);
    }
}
