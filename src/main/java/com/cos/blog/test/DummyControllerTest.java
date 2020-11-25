package com.cos.blog.test;


import com.cos.blog.model.Role;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Supplier;

@RestController
public class DummyControllerTest {

    @Autowired//의존성 주입
    private UserRepository userRepository;

    @Transactional // added for another way of update, Dirty checking = although you don't use save function it updates data. when function is finished it and if data is edited, it does commit
    @PutMapping("/dummy/user/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User requestUser){ //(@RequestBody)Request json data (spring MessageConverter Jackson Library) => Java Object
        System.out.println("id: "+ id);
        System.out.println("password: " + requestUser.getPassword());
        System.out.println("email: " + requestUser.getEmail());

        User user = userRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("failed");
        });
        user.setPassword(requestUser.getPassword());
        user.setEmail(requestUser.getEmail());


        //if i don't pass id save function do insert
        //if i do pass id and there's a matched id save function do update
        //if if do pass id and there's no matched id save function do insert
        //userRepository.save(user); commented out for another way of update
        return null;
    }

    @GetMapping("/dummy/users")
    public List<User> list(){
        return userRepository.findAll();
    }

    //한페이지당 2건의 데이터
    @GetMapping("/dummy/user")
    public List<User> pageList(@PageableDefault(size=1, sort = "id", direction = Sort.Direction.DESC)Pageable pageable){
//        Page<User> pagingUser = userRepository.findAll(pageable);
//        if(pagingUser.isLast()){
//
//        }
        List<User> users = userRepository.findAll(pageable).getContent();
        return users;
    }

    //http://localhost:8085/blog/dummy/user/3
    @GetMapping("dummy/user/{id}")
    public User detail(@PathVariable int id){
        //Lambda
        //User user = userRepository.findById(id).orElseThrow(()->{
        //  return new IllegalArgumentException("No user found");
        // });

        User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
            @Override
            public IllegalArgumentException get() {
                return new IllegalArgumentException("Cannot find the user.");
            }
        });
        //RestController는 data를 return하는데 객체는 Browser가 이해못한다
        // json으로 변환해야함
        //스프링부트는 MessageConverter가 응답시에 자동 작동해 Jackson이라는 라이브러리를 호출해 user object를 json으로 변환해준다.
        return user;
    }

    @PostMapping("/dummy/join")
    public String join(User user){
        System.out.println("id: " + user.getId());
        System.out.println("username: " + user.getUsername());
        System.out.println("passwrod: " + user.getPassword());
        System.out.println("email: " + user.getEmail());
        System.out.println("role: " + user.getRole() );
        System.out.println("createDate: " + user.getCreateDate());

        user.setRole(Role.USER);
        userRepository.save(user);
        return "Registration success";
    }
}
//key=value 형태가 아닌 json data 같은 일반 text데이터는 스프링 컨트롤러가 받기 위해서는 @RequestBody annotation을 사용해야한다.
//@RequestBody를 붙이면 MessageConverter class를 구현한 jackson library가 발동하면서 json data를 java object로 parsing하여 받아준다.