package com.astarell.Bloggy.services;

import com.astarell.Bloggy.DTOs.AccountDTO;
import com.astarell.Bloggy.models.Account;
import com.astarell.Bloggy.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final AccountRepository accountRepo;

    @Autowired
    public AccountService(AccountRepository accountRepo){
        this.accountRepo = accountRepo;
    }

    public Optional<Account> findAccountByUsername(String username){
        return accountRepo.findAccountByUsername(username);
    }

    public Optional<Account> findAccountByEmail(String email){
        return accountRepo.findAccountByEmail(email);
    }

    public Collection<AccountDTO> findAll(){
        return accountRepo.findAll().stream().map(this::entityToAccountDTO).collect(Collectors.toList());
    }

    public void save(Account account){
        accountRepo.save(account);
    }

    private AccountDTO entityToAccountDTO(Account entity){
        return new AccountDTO(entity.getId(), entity.getUsername(), entity.getEmail(),
                entity.getAccountPosts(), entity.getComments(), entity.getAuthorities());
    }
}
