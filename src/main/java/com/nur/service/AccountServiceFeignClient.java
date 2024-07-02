package com.nur.service;

import com.nur.dto.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@FeignClient(name = "ACCOUNT-MGT-SERVICE")
public interface AccountServiceFeignClient {

    @GetMapping("/account/{accountNumber}")
    Optional<Account> getAccountByAccountNumber(@PathVariable("accountNumber") String accountNumber);

    @PutMapping("/account/update")
    void updateAccount(@RequestBody Account account);
}
