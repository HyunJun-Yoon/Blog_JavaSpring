package com.cos.blog.test;

import org.springframework.web.bind.annotation.*;

//사용자 요청 -> 응답 (HTML file)
//@Controller

//사용자 요청 -> 응답(DATA)
//@RestController

@RestController
public class HttpControllerTest {

    private static final String TAG = "HttpControllerTest: ";

    @GetMapping("/http/lombok")
    public String lombokTest(){
       // Member m = new Member(1, "ssar", "1234", "email"); without lombok bulider
        Member m = Member.builder().username("ssar").password("1234").email("ssar@gmail.com").build();

        System.out.println(TAG + "getter: " + m.getId());
        m.setId(5000);
        System.out.println(TAG + "setter: " + m.getId());
        return "lombok test complete";
    }

    //인터넷 브라우저 요청은 무조건 get요청 밖에 할 수 없다.

    //select
    @GetMapping("/http/get")
    public String getTest(Member m){
        //@RequestParam int id, @RequestParam String username
        return "request get " + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail();
    }

    //insert
    @PostMapping("/http/post") //post는 query string(/?id=1)이 아닌 body에 데이터를 담아 보내야한다.
    public String postTest(@RequestBody  Member m){ //json data가 body에 담겨 오면 MessageConverter(Spring boot) 가 매핑해준다.
        return "request post " + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail();
    }

    //update
    @PutMapping("/http/put")
    public String putTest(@RequestBody Member m){
        return "request put " + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail();
    }


    //delete
    @DeleteMapping("/http/delete")
    public String deleteTest(){
        return "request delete";
    }
}
