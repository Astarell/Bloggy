package com.astarell.Bloggy.repositories;

import com.astarell.Bloggy.customdaos.AccountDaoBasic;
import com.astarell.Bloggy.models.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long>, AccountDaoBasic<Account> {
    Optional<Account> findAccountByUsername(String username);
    Optional<Account> findAccountByEmail(String email);
    Collection<Account> findAll();
}
