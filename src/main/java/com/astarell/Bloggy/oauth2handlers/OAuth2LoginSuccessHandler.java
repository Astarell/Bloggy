package com.astarell.Bloggy.oauth2handlers;

import com.astarell.Bloggy.models.Account;
import com.astarell.Bloggy.services.AccountService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final AccountService accountService;
    @Autowired
    public OAuth2LoginSuccessHandler(AccountService accountService){
        this.accountService = accountService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;

        if("github".equals(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId())){
            DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();

            Map<String, Object> attributes = principal.getAttributes();
            String username = attributes.getOrDefault("login", "").toString();
            String email = attributes.getOrDefault("email", "").toString();

            logger.info("LOGGER => " + username + " " + email);

            accountService.findAccountByEmail(email).ifPresentOrElse(user -> {
                DefaultOAuth2User newUser = new DefaultOAuth2User(user.getAuthorities(), attributes, "id");
                Authentication securityAuth = new OAuth2AuthenticationToken(newUser, user.getAuthorities(),
                        oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());
                SecurityContextHolder.getContext().setAuthentication(securityAuth);
                logger.info("LOGGER => FOUND USER: " + username + " " + email);
            }, () -> {
                logger.info("LOGGER => USER NOT FOUND" + username + " " + email);
                Account account = new Account();
                if(accountService.findAccountByUsername(username).isPresent()){
                    account.setUsername(username + email.substring(0, 5));
                }
                account.setEmail(email);
                accountService.save(account);

                DefaultOAuth2User newUser = new DefaultOAuth2User(account.getAuthorities(), attributes, "id");
                Authentication securityAuth = new OAuth2AuthenticationToken(newUser, account.getAuthorities(),
                        oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());
                SecurityContextHolder.getContext().setAuthentication(securityAuth);
            });
        }
        this.setAlwaysUseDefaultTargetUrl(true);
        this.setDefaultTargetUrl("http://localhost:9191/");
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
