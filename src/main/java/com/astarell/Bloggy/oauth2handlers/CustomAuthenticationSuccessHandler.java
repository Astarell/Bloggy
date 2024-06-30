package com.astarell.Bloggy.oauth2handlers;

import com.astarell.Bloggy.models.Account;
import com.astarell.Bloggy.repositories.AccountRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Map;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private AccountRepository accountRepo;
    private String homeUrl = "http://localhost:9191/";

    @Autowired
    public CustomAuthenticationSuccessHandler(AccountRepository accountRepo){
        this.accountRepo = accountRepo;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        if(response.isCommitted()){
            return;
        }

        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();

        Map attributes = oAuth2User.getAttributes();
        String email = (String)attributes.get("email");
        // ПРОВЕРКУ НА NULL
        Account account = accountRepo.findAccountByEmail(email).get();
        String token = JwtTokenUtil.generateToken(account);

        String redirectionUrl = UriComponentsBuilder.fromUriString(homeUrl)
                .queryParam("auth_token", token)
                .build().toUriString();

        getRedirectStrategy().sendRedirect(request, response, redirectionUrl);
    }
}
