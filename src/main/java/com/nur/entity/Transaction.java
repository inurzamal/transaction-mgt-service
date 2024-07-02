package com.nur.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String accountNumber;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private String transactionType; // e.g., "debit", "credit"
    private String status; // e.g., "pending", "completed", "failed"
}
