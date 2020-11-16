package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempControllerTest {

    @GetMapping("/temp/home")
    public String tempHome(){
        //file return basic route = src/main/resources/static
        System.out.println("temp Home");
        return "/home.html";
    }

    @GetMapping("temp/jsp")
    public String tempJsp(){
        //yml file에서 경로를 추가
        //route = /WEB-INF/views/test.jsp
        return "test";
    }

}
