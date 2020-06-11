package com.example.demo.exceptions;

import net.bytebuddy.asm.Advice;
import org.apache.catalina.User;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.acls.model.AlreadyExistsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;

@ControllerAdvice
public class myExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {UsernameNotFoundException.class, NoSuchElementException.class})
    public ResponseEntity<ResponsePayload> handelExceptions(Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponsePayload(HttpStatus.NOT_FOUND.toString(), e.getMessage()));
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ResponsePayload> HandleUserAlreadyState(AlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponsePayload(HttpStatus.CONFLICT.toString(), ex.getMessage()));

    }

    @ExceptionHandler(UserNotActiveException.class)
    public ResponseEntity<ResponsePayload> HandleUserNotActiveState(UserNotActiveException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponsePayload(HttpStatus.UNAUTHORIZED.toString(), ex.getMessage()));

    }


    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.status(status).body(new ResponsePayload(status.toString(), ex.getMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        System.out.println("myexception " + ex.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsePayload(HttpStatus.BAD_REQUEST.toString(), "JSON Exception : there is SOME Error with your JSON see details /n " + ex));

    }

    @ExceptionHandler(value = {SQLException.class, ConstraintViolationException.class})
    public ResponseEntity<ResponsePayload> handleSqlExceptio(Exception ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponsePayload(HttpStatus.CONFLICT.toString(), "there is Some Sql Exception" + ex));
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.badRequest().body(new ResponsePayload(status.toString(), ex.toString()));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.badRequest().body(new ResponsePayload(status.toString(), ex.toString()));
    }


    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.badRequest().body(new ResponsePayload(status.toString(), ex.getMessage()));
    }

    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<ResponsePayload> hanleemptytokenPassing(NullPointerException ex) {
        return ResponseEntity.badRequest().body(new ResponsePayload(HttpStatus.BAD_REQUEST.toString(), "add 'Authorization' Header details exception : " + ex.getMessage()));
    }

    @ExceptionHandler(value = {InterruptedException.class, ExecutionException.class})
    public ResponseEntity<ResponsePayload> handleFireBaseExceptions(InterruptedException e1, ExecutionException e2) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponsePayload(HttpStatus.INTERNAL_SERVER_ERROR.toString()
                        , "failed retrieving data try again one more " + e1.getMessage() + e2.getMessage()));

    }
}
