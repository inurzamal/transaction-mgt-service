package com.nur.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class Account {
    private Long id;
    private String accountNumber;
    private String accountHolderName;
    private BigDecimal balance;
    private AccountHolderAddress address;
}
