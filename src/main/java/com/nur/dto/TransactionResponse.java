package com.nur.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionResponse {
    private Long id;
    private String accountNumber;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private String transactionType;
    private String status;
}