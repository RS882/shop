package de.aittr.g_38_jp_shop.exception_handler;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {

    private String message;
    private String additionalMessage;

    public Response(String message) {
        this.message = message;
    }
}
