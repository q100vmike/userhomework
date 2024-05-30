package ru.mtshomework.userweb.controller;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.mtshomework.userweb.entity.User;
import ru.mtshomework.userweb.exception.CustomException;
import ru.mtshomework.userweb.exception.UserNotFoundException;

import java.util.HashMap;
import java.util.Map;

@Log4j
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
    public String concatParam(@PathVariable String id, @RequestParam(defaultValue ="Guest", required=false) String name) {
        return "Id= " + id + " name=" + name;
    }

    @GetMapping("/usernof/{id}")
    public String userNof(@PathVariable String id) throws UserNotFoundException {
        if ("000".equals(id)){
            throw new UserNotFoundException();
        }
        return "Id= " + id;
    }

    @GetMapping("/argument/{id}")
    public String argument(@PathVariable String id) throws IllegalArgumentException {
        if ("111".equals(id)){
            throw new IllegalArgumentException();
        }
        return "Id= " + id;
    }

    @GetMapping("/header")
    public ResponseEntity<String> header(@RequestHeader HttpHeaders headers) {
        log.info("************Headers:******************");
        String url = headers.get("User-Agent").get(0);
        return new ResponseEntity<String>(String.format("User-Agent = %s", url), HttpStatus.OK);
    }

    @GetMapping("/customex")
    public String customex(@RequestHeader HttpHeaders headers) {
        throw new CustomException("CustomException 404");
       // return "";
    }

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

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundExceptions(UserNotFoundException ex) {
        return new ResponseEntity<Object>(
                "Id не может быть 000!", new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

/*    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "CustomException CustomException!")
    @ExceptionHandler(value = CustomException.class)
    public void handleCustomException(CustomException ex) {
            //nothing
    }*/

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<String>(
                "IllegalArgumentException!", new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
