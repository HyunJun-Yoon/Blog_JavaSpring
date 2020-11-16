package com.cos.blog.test;


import lombok.*;

//@Getter
//@Setter
//@Data //getter and setter
//@AllArgsConstructor
//@RequiredArgsConstructor final 붙은 애들의 constructor 만들어준다

@Data
//@AllArgsConstructor
@NoArgsConstructor // Bean constructor
public class Member {
    private int id;
    private String username;
    private String password;
    private String email;

    @Builder
   public Member(int id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }
/*
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }*/
}
