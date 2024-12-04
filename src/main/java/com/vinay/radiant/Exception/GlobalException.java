package com.vinay.radiant.Exception;

import com.vinay.radiant.dto.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> exceptionHandler(Exception e, WebRequest wr){
        ErrorDetails errorDetails = new ErrorDetails(
                e.getMessage(), wr.getDescription(false),
                LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

   @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDetails> userNotFoundExceptionHandler(Exception e, WebRequest wr){
        ErrorDetails errorDetails = new ErrorDetails(
                e.getMessage(), wr.getDescription(false),
                LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

 @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorDetails> postNotFoundExceptionHandler(Exception e, WebRequest wr){
        ErrorDetails errorDetails = new ErrorDetails(
                e.getMessage(), wr.getDescription(false),
                LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

 @ExceptionHandler(ChatNotFoundException.class)
    public ResponseEntity<ErrorDetails> chatNotFoundExceptionHandler(Exception e, WebRequest wr){
        ErrorDetails errorDetails = new ErrorDetails(
                e.getMessage(), wr.getDescription(false),
                LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

 @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ErrorDetails> commentNotFoundExceptionHandler(Exception e, WebRequest wr){
        ErrorDetails errorDetails = new ErrorDetails(
                e.getMessage(), wr.getDescription(false),
                LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }



}
