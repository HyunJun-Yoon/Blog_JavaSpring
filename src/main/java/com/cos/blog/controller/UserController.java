package com.cos.blog.controller;

import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;



//accessible auth/**, /
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Value("%{cos.key}")
    private String cosKey;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/auth/joinForm")
    public String joinForm(){

        return "user/joinForm";
    }

    @GetMapping("/auth/loginForm")
    public String loginForm(){

        return "user/loginForm";
    }

    @GetMapping("/auth/kakao/callback")
    public String kakaoCallback(String code){ //data를 리턴해주는 컨트롤러 함수
        //POST 방식으로 key=value 데이터를 요청(카카오 쪽으로)
        //libraries: Retrofit2, OkHttp, RestTemplate
        RestTemplate rt = new RestTemplate();

        //HttpBody Object
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "892cfeff253634b6e65a2c4bb90ea1f4");
        params.add("redirect_uri", "http://localhost:8085/auth/kakao/callback");
        params.add("code", code);

        //HttpHeader and HttpBody
        HttpEntity<MultiValueMap<String,String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        //Http request(POST)
        ResponseEntity<String> responseEntity = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        //put json data into an object
        //Libraries = Gson, Json Simple, ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oauthToken = null;
        try {
            oauthToken = objectMapper.readValue(responseEntity.getBody(), OAuthToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println("kakao access token: " + oauthToken.getAccess_token());

        RestTemplate rt2 = new RestTemplate();

        //HttpBody Object
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + oauthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HttpHeader and HttpBody
        HttpEntity<MultiValueMap<String,String>> kakaoProfileRequest =
                new HttpEntity<>(headers2);

        //Http request(POST)
        ResponseEntity<String> responseEntity2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );
        System.out.println(responseEntity2.getBody());

        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper2.readValue(responseEntity2.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        //User object: username, password, email
        System.out.println("kakao id: " + kakaoProfile.getId());
        System.out.println("kakao email: " + kakaoProfile.getKakao_account().getEmail());
        System.out.println("blog server username: " + kakaoProfile.getKakao_account().getEmail());
        System.out.println("blog server email: " + kakaoProfile.getKakao_account().getEmail());
        System.out.println("blog server password: " + cosKey);

        //register user
        User kakaoUser = User.builder()
                .username(kakaoProfile.getKakao_account().getEmail())
                .password(cosKey.toString())
                .email(kakaoProfile.getKakao_account().getEmail())
                .oauth("kakao")
                .build();

        //user existence chekcing
        User user = userService.existenceChecking(kakaoUser.getUsername());
        if(user.getUsername() == null){
            userService.register(kakaoUser);
        }

        //login
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), cosKey));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/";

    }

    @GetMapping("/user/updateForm")
    public String updateForm(){
        return "user/updateForm";
    }

}
