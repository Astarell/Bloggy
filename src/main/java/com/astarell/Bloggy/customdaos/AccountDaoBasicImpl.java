package com.astarell.Bloggy.customdaos;

import com.astarell.Bloggy.models.Account;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class AccountDaoBasicImpl implements AccountDaoBasic<Account>{
    @PersistenceContext
    private EntityManager em;
    @Override
    public void update(Account patchObject) {
        em.merge(patchObject);
    }
}
