package de.aittr.g_38_jp_shop.exception_handler.exceptions;

public class ProductServiceException extends RuntimeException{

    public ProductServiceException(String message) {
        super(message);
    }

    public ProductServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
