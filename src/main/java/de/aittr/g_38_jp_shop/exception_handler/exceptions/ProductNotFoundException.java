package de.aittr.g_38_jp_shop.exception_handler.exceptions;

public class ProductNotFoundException  extends RuntimeException{
    public ProductNotFoundException(String message) {
        super(message);
    }
}
