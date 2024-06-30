package com.astarell.Bloggy.oauth2handlers;

import com.astarell.Bloggy.models.Account;
import com.astarell.Bloggy.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private AccountRepository accountRepo;
    @Autowired
    public CustomOAuth2UserService(AccountRepository accountRepo){
        this.accountRepo = accountRepo;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map attributes = oAuth2User.getAttributes();
        CustomOAuthUserInfo userInfo = new CustomOAuthUserInfo();

        userInfo.setEmail((String)attributes.get("email"));
        userInfo.setName((String)attributes.get("login"));
        userInfo.setImageUrl((String)attributes.get("avatar_url"));
        userInfo.setId((Integer) attributes.get("id"));
        updateUser(userInfo);

        return oAuth2User;
    }

    private void updateUser(CustomOAuthUserInfo userInfo){
        Account account = accountRepo.findAccountByEmail(userInfo.getEmail()).orElse(null);
        if(account == null){
            account = new Account();
        }
        account.setUsername(userInfo.getName());
        account.setEmail(userInfo.getEmail());

        accountRepo.save(account);
    }
}
