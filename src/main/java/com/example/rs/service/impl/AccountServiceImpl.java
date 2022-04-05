package com.example.rs.service.impl;

import com.example.rs.dao.AccountMapper;
import com.example.rs.entity.Account;
import com.example.rs.service.inf.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public Account getAccountById(String accountId) {
        return accountMapper.getAccountById(accountId);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountMapper.getAllAccounts();
    }

    @Override
    public Account insertAccount(Account account) {
        return accountMapper.insertAccount(account);
    }

    @Override
    public int deleteAccount(Account account) {
        return accountMapper.deleteAccount(account);
    }
}
