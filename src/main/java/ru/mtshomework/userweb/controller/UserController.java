package ru.mtshomework.userweb.controller;

import ch.qos.logback.core.util.StringUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.mtshomework.userweb.entity.User;
import ru.mtshomework.userweb.exception.UserNotFoundException;

import java.util.HashMap;
import java.util.Map;


@RestController
public class UserController {

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello World";
    }

    @PostMapping("/greet")
    public String postGreet(@Valid User user) {
        return "Успешная регистрация";
    }

    @GetMapping("/concat/{id}")
    public String concatParam(@PathVariable String id, @RequestParam String name) {
        return "Id= " + id + " name=" + name;
    }

    @GetMapping("/usernof/{id}")
    public String userNof(@PathVariable String id) throws UserNotFoundException {
        throw new UserNotFoundException();
/*        if (StringUtil.isNullOrEmpty(id)){
            throw new UserNotFoundException();
        }
        return "Id= " + id;*/
    }

/*
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
*/

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundExceptions(UserNotFoundException ex) {
        return new ResponseEntity<Object>(
                "Access denied message here", new HttpHeaders(), HttpStatus.FORBIDDEN);
    }
}
