package com.cos.blog.service;

import com.cos.blog.model.Role;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


//IoC
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional(readOnly = true)
    public User existenceChecking(String username){
        User user = userRepository.findByUsername(username).orElseGet(()->{
            return new User();
        });

        return user;
    }


    @Transactional
    public void register(User user){
        String rawPassword = user.getPassword();
        String encPassword = encoder.encode(rawPassword); //hash
        user.setPassword(encPassword);
        user.setRole(Role.USER);
        userRepository.save(user);
    }

    @Transactional
    public void update(User user){
        //수정 시에는 영속성 컨텍스트 user 오브젝트를 영속화시키고 영속화된 user 오브젝트를 수정
        //select를 해서 user 오브젝트를 db로 부터 가져오는 이유는 영속화를 하기 위해서
        //영속화된 오브젝트를 변경하면 자동으로 db에 udpate문을 날려줌
        User persistence = userRepository.findById(user.getId()).orElseThrow(()->{
            return new IllegalArgumentException("Cannot find the user");
        });

        //Validate check
        if(persistence.getOauth() == null || persistence.getOauth().equals("")) {
            String rawPassword = user.getPassword();
            String encPassword = encoder.encode(rawPassword);
            persistence.setPassword(encPassword);
            persistence.setEmail(user.getEmail());
        }
        //회원 수정 함수 종료 시 = 서비스 종료 시 = Transaction 종료 = commit이 자동으로 됨
        //영속화된 persistence 객체의 변화가 감지되면 (dirty checking) update를 해준다.


    }

//    @Transactional(readOnly = true) //select할 때 transaction시작, 서비스 종료 시에 transaction 종료(정합성)
//    public User login(User user){
//        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//    }
}
