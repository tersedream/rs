package com.example.rs.service.inf;

import com.example.rs.entity.Account;

import java.util.List;


public interface AccountService {
    List<Account> getAllAccounts();
    Account insertAccount(Account account);
    int deleteAccount(Account account);
    Account getAccountById(String accountId);
}
