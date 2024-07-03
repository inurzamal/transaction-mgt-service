package com.nur.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class AccountResponse {

    private Long id;

    private String accountNumber;

    private String accountHolderName;

    private String email;

    private String phoneNo;

    private String pan;

    private BigDecimal balance;

    private AccountHolderAddress address;

}
