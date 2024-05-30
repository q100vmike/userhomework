package ru.mtshomework.userweb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "CustomException CustomException!")
public class CustomException extends RuntimeException{
    private static final long serialVersionUID = -7630066735832452689L;
    public CustomException(String message) {
        super(message);
    }
}
