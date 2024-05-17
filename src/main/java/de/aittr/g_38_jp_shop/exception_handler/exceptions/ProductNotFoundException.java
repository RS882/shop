package de.aittr.g_38_jp_shop.exception_handler.exceptions;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String productTitle) {

        super(String.format("Product with title %s not found", productTitle));
    }
}
