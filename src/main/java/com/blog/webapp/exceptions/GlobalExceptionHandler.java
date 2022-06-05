package com.blog.webapp.exceptions;

import com.blog.webapp.payloads.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFound(ResourceNotFoundException resourceNotFoundException){
        String msg = resourceNotFoundException.getMessage();
        return new ResponseEntity<ApiResponse>(new ApiResponse(msg,false), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> methodArgumentNotValid(MethodArgumentNotValidException e){

        Map<String, String> errors = new HashMap<>();
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();

        allErrors.forEach(objectError ->{
            String field = ((FieldError)objectError).getField();
            String defaultMessage = objectError.getDefaultMessage();
            errors.put(field, defaultMessage);
        } );

        return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
    }


}
