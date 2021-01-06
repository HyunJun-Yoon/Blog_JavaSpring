package com.cos.blog.config.auth;

import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Value("%{cos.key}")
    private String cosKey;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getEmail();
        User googleUser = User.builder()
                .username(email)
                .password(cosKey.toString())
                .email(email)
                .oauth("google")
                .build();
        User user = userService.existenceChecking(email);
        if(user.getUsername() == null){
            userService.register(googleUser);
        }
        System.out.println("**********User email********* " + email);
        Authentication authenticationSe = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, cosKey));
        SecurityContextHolder.getContext().setAuthentication(authenticationSe);
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
