package com.nur.controller;

import com.nur.dto.TransactionRequest;
import com.nur.dto.TransactionResponse;
import com.nur.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;


    @PostMapping("transaction/create")
    public ResponseEntity<TransactionResponse> createTransaction(@RequestBody TransactionRequest transactionRequest) {
        try {
            TransactionResponse createdTransaction = transactionService.createTransaction(transactionRequest);
            return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
        } catch (ResponseStatusException ex) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @GetMapping("transaction/history/{accountNumber}")
    public ResponseEntity<List<TransactionResponse>> getTransactionHistory(@PathVariable String accountNumber) {
        try {
            List<TransactionResponse> transactions = transactionService.getTransactionHistory(accountNumber);
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (ResponseStatusException ex) {
            return new ResponseEntity<>(null, ex.getStatusCode());
        }
    }

    @GetMapping("transaction/status/{id}")
    public ResponseEntity<?> getTransactionStatus(@PathVariable Long id) {
        try {
            TransactionResponse transaction = transactionService.getTransactionStatus(id);
            return new ResponseEntity<>(transaction, HttpStatus.OK);
        } catch (ResponseStatusException ex) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

}
