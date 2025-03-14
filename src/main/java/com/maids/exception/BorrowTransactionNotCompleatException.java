package com.maids.exception;

public class BorrowTransactionNotCompleatException extends RuntimeException{
    public BorrowTransactionNotCompleatException(String message){
        super(message);
    }
}
