package com.nur.service;

import com.nur.dto.*;
import com.nur.entity.Transaction;
import com.nur.exceptions.ServiceNotAvailableException;
import com.nur.exceptions.TransactionNotFoundException;
import com.nur.repository.TransactionRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private static final Logger LOGGER = LoggerFactory.getLogger("TransactionService.class");

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountServiceFeignClient accountClient;

    @Transactional
    @CircuitBreaker(name = "accountService", fallbackMethod = "fallbackCreateTransaction")
    public TransactionResponse createTransaction(TransactionRequest transactionRequest) {
        Transaction transaction = mapToTransaction(transactionRequest);

        // Process the transaction
        boolean success = processTransaction(transaction);
        if (success) {
            transaction.setStatus("completed");
        } else {
            transaction.setStatus("failed");
        }
        transaction = transactionRepository.save(transaction);
        return mapToTransactionResponse(transaction);
    }

    @CircuitBreaker(name = "accountService", fallbackMethod = "fallbackGetTransactionHistory")
    public List<TransactionResponse> getTransactionHistory(String accountNumber) {
        List<Transaction> transactions = transactionRepository.findByAccountNumber(accountNumber);
        return transactions.stream()
                .map(this::mapToTransactionResponse)
                .toList();
    }

    public TransactionResponse getTransactionStatus(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException());
        return mapToTransactionResponse(transaction);
    }

    private Transaction mapToTransaction(TransactionRequest transactionRequest) {
        Transaction transaction = new Transaction();
        transaction.setAccountNumber(transactionRequest.getAccountNumber());
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setTransactionType(transactionRequest.getTransactionType());
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setStatus("pending");
        return transaction;
    }

    private boolean processTransaction(Transaction transaction) {
        // Call AccountService to get account details
        try {
            Optional<AccountResponse> accountOpt = accountClient.getAccountByAccountNumber(transaction.getAccountNumber());
            if (accountOpt.isPresent()) {
                AccountResponse account = accountOpt.get();
                if ("debit".equalsIgnoreCase(transaction.getTransactionType())) {
                    if (account.getBalance().compareTo(transaction.getAmount()) >= 0) {
                        account.setBalance(account.getBalance().subtract(transaction.getAmount()));
                        accountClient.updateAccount(account.getId(), account);
                        return true;
                    } else {
                        return false; // Insufficient balance
                    }
                } else if ("credit".equalsIgnoreCase(transaction.getTransactionType())) {
                    account.setBalance(account.getBalance().add(transaction.getAmount()));
                    accountClient.updateAccount(account.getId(), account);
                    return true;
                }
            }
            return false; // Account not found or other failure
        } catch (Exception e) {
            throw new ServiceNotAvailableException(e);
        }
    }

    private TransactionResponse mapToTransactionResponse(Transaction transaction) {
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setId(transaction.getId());
        transactionResponse.setAccountNumber(transaction.getAccountNumber());
        transactionResponse.setAmount(transaction.getAmount());
        transactionResponse.setTransactionDate(transaction.getTransactionDate());
        transactionResponse.setTransactionType(transaction.getTransactionType());
        transactionResponse.setStatus(transaction.getStatus());
        return transactionResponse;
    }

    // Fallback methods
    public TransactionResponse fallbackCreateTransaction(TransactionRequest transactionRequest, Throwable t) {
        LOGGER.info("Fallback method for createTransaction called : {}, exception: {}", transactionRequest, t.getMessage());
        throw new ServiceNotAvailableException();
    }

    public List<TransactionResponse> fallbackGetTransactionHistory(String accountNumber, Throwable t) {
        LOGGER.info("Fallback method for getTransactionHistory called: {}, {}", accountNumber, t.getMessage());
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Service is down. Please try again later.");
    }

}
