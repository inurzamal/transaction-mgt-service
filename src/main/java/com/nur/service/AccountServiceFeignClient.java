package com.nur.service;

import com.nur.dto.AccountResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@FeignClient(name = "ACCOUNT-MGT-SERVICE")
public interface AccountServiceFeignClient {

    @GetMapping("/account/{accountNumber}")
    Optional<AccountResponse> getAccountByAccountNumber(@PathVariable("accountNumber") String accountNumber);

    @PutMapping("/account/update/{id}")
    void updateAccount(@PathVariable("id") Long id, @RequestBody AccountResponse accountResponse);
}
