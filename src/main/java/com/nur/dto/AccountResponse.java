package com.nur.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class AccountResponse {

    private Long id;
    private String accountNumber;
    private BigDecimal balance;
    private Long customerId; // Reference to Customer ID
}
