package de.aittr.g_38_jp_shop.exception_handler;


import de.aittr.g_38_jp_shop.exception_handler.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ThirdTestException.class)
    public ResponseEntity<Response> handleException(ThirdTestException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.I_AM_A_TEAPOT);
    }

    @ExceptionHandler(FourthTestException.class)
    public ResponseEntity<Response> handleException(FourthTestException e) {
        Throwable childException = e.getCause();
        Response response = new Response(e.getMessage(),
                childException == null ? null : parseExceptionMessage(childException.getMessage()));
        return new ResponseEntity<>(response, HttpStatus.I_AM_A_TEAPOT);
    }

    private String parseExceptionMessage(String message) {
        // При желании здесь можно реализовать логику, которая из сообщения
        // выделяет только нужную информацию
        return message;
    }
    //------------------------------------------

    @ExceptionHandler(ProductServiceException.class)
    public ResponseEntity<Response> handleException(ProductServiceException e) {

        Throwable childException = e.getCause();
        HttpStatus httpStatus = HttpStatus.I_AM_A_TEAPOT;

        if (childException == null) {
            Response response = new Response(e.getMessage());
            return new ResponseEntity<>(response,httpStatus );
        }

        Response response = new Response(e.getMessage(),childException.getMessage());
          Class<? extends Throwable> causeClass = childException.getClass();

        if (causeClass.equals(IncorrectIdException.class) ||
                causeClass.equals(IncorrectTitleException.class)) {
            httpStatus = HttpStatus.BAD_REQUEST;
        } else if (causeClass.equals(ProductNotFoundException.class)) {
            httpStatus = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(response, httpStatus);
    }

    @ExceptionHandler(IncorrectIdException.class)
    public ResponseEntity<Response> handleException(IncorrectIdException e) {
        return new ResponseEntity<>(new Response(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IncorrectTitleException.class)
    public ResponseEntity<Response> handleException(IncorrectTitleException e) {
        return new ResponseEntity<>(new Response(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Response> handleException(ProductNotFoundException e) {
        return new ResponseEntity<>(new Response(e.getMessage()), HttpStatus.NOT_FOUND);
    }
}

