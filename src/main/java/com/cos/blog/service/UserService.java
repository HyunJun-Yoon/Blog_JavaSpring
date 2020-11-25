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

    @Transactional
    public void register(User user){
        String rawPassword = user.getPassword();
        String encPassword = encoder.encode(rawPassword); //hash
        user.setPassword(encPassword);
        user.setRole(Role.USER);
        userRepository.save(user);
    }

//    @Transactional(readOnly = true) //select할 때 transaction시작, 서비스 종료 시에 transaction 종료(정합성)
//    public User login(User user){
//        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//    }
}
