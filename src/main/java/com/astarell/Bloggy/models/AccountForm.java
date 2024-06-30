package com.astarell.Bloggy.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class AccountForm {
    @Length(min = 5, message = "Username should have more than 5 symbols")
    @Length(max = 30, message = "Username should be less than 30 symbols")
    @NotEmpty(message = "Username field shouldn't be empty")
    private String username;

    @NotEmpty(message = "Password field shouldn't be empty")
    @Length(min = 5, message = "Password should have more than 5 symbols")
    @Length(max = 120, message = "Password should be less than 120 symbols")
    private String password;

    public Account toAccount(PasswordEncoder encoder){
        return new Account(username, encoder.encode(password));
    }
}
