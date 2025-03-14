package com.maids.exception;
import com.maids.entities.CustomResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoPatrensFoundException.class)
    public final ResponseEntity<Object> handleNoPatrensFoundException(NoPatrensFoundException ex, WebRequest request){
//        List<String> resList = new ArrayList<>();
//        resList.add(ex.getLocalizedMessage());
        CustomResponseEntity<String> errorResponse = new CustomResponseEntity<>(HttpStatus.NOT_FOUND.toString(), ex.getLocalizedMessage(), null);
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }
    @ExceptionHandler(NoBookFoundException.class)
    public final ResponseEntity<Object> handleNoBookFoundException(NoBookFoundException ex, WebRequest request){
        CustomResponseEntity<String> errorResponse = new CustomResponseEntity<>(HttpStatus.NOT_FOUND.toString(), ex.getLocalizedMessage(), null);
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }

    @ExceptionHandler(BorrowTransactionNotCompleatException.class)
    public final ResponseEntity<Object> handleBorrowTransactionNotCompleatException(BorrowTransactionNotCompleatException ex, WebRequest request){
        CustomResponseEntity<String> errorResponse = new CustomResponseEntity<>(HttpStatus.NOT_FOUND.toString(), ex.getLocalizedMessage(), null);
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }
    @ExceptionHandler(ReturnTransactionNotCompleatException.class)
    public final ResponseEntity<Object> handleReturnTransactionNotCompleatException(ReturnTransactionNotCompleatException ex, WebRequest request){
        CustomResponseEntity<String> errorResponse = new CustomResponseEntity<>(HttpStatus.NOT_FOUND.toString(), ex.getLocalizedMessage(), null);
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }

    @ExceptionHandler(AuthException.class)
    public final ResponseEntity<Object> handleAuthException(AuthException ex, WebRequest request){
        CustomResponseEntity<String> errorResponse = new CustomResponseEntity<>(HttpStatus.NOT_FOUND.toString(), ex.getLocalizedMessage(), null);
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }
    @ExceptionHandler(TokenException.class)
    public final ResponseEntity<Object> handleTokenException(TokenException ex, WebRequest request){
        CustomResponseEntity<String> errorResponse = new CustomResponseEntity<>(HttpStatus.NOT_FOUND.toString(), ex.getLocalizedMessage(), null);
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }
}
