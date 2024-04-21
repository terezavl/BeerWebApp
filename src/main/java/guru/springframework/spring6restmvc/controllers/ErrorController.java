package guru.springframework.spring6restmvc.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ErrorController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleBindExceptions(MethodArgumentNotValidException exception){
        List errorList = exception.getFieldErrors().stream()
                .map(fieldError -> {
                    Map<String, String> map = new HashMap<>();
                    map.put(fieldError.getField(), fieldError.getDefaultMessage());
                    return map;
                }).toList();

        return ResponseEntity.badRequest().body(errorList);
    }
}
