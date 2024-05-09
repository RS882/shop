package de.aittr.g_38_jp_shop.exception_handler.exceptions;

public class FirstTestException extends RuntimeException {

    public FirstTestException(String message) {
        super( message);
    }
// cause - причина
    public FirstTestException(String message, Throwable cause) {
        super(message, cause);
    }
}
