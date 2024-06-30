package com.astarell.Bloggy.controllers;

import com.astarell.Bloggy.models.AccountForm;
import com.astarell.Bloggy.services.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final PasswordEncoder passEncoder;
    private final AccountService accountService;
    @Autowired
    public RegisterController(PasswordEncoder passEncoder,
                              AccountService accountService){
        this.passEncoder = passEncoder;
        this.accountService = accountService;
    }

    @ModelAttribute(name = "account_form")
    public AccountForm accountForm(){
        return new AccountForm();
    }

    @GetMapping
    public String registerPage(){
        return "auth-related/user-register";
    }

    @PostMapping
    public String processRegistration(@Valid @ModelAttribute(name = "account_form") AccountForm accountForm,
                                      BindingResult errors){
        if(errors.hasErrors()){
            return "auth-related/user-register";
        }

        if(accountService.findAccountByUsername(accountForm.getUsername()).isPresent()){
            ObjectError error = new ObjectError("existingAccount",
                    "Account with this username is already exist");
            errors.addError(error);
            return "auth-related/user-register";
        }

        accountService.save(accountForm.toAccount(passEncoder));
        return "redirect:/login";
    }

}
