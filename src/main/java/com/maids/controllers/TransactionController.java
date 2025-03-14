package com.maids.controllers;

import com.maids.entities.CustomResponseEntity;
import com.maids.entities.Transactions;
import com.maids.services.TransactionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @Transactional
    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<Object> borrowBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        transactionService.borrowBook(patronId, bookId);
        CustomResponseEntity<Transactions> response = new CustomResponseEntity<>(HttpStatus.OK.toString(), "Success", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @Transactional
    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<Object> returnBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        transactionService.returnBook(patronId, bookId);
        CustomResponseEntity<Transactions> response = new CustomResponseEntity<>(HttpStatus.OK.toString(), "Success", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
