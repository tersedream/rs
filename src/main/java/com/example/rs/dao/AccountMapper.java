package com.example.rs.dao;

import com.example.rs.entity.Account;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AccountMapper {
    List<Account> getAllAccounts();
    Account getAccountById(String accountId);
    Account insertAccount(Account account);
    int deleteAccount(Account account);
}
