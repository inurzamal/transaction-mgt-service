package com.nur.dto;

import lombok.Data;

@Data
public class AccountHolderAddress {
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
}