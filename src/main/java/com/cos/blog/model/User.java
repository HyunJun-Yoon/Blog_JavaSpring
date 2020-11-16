package com.cos.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;


//ORM  -> JAVA(다른 언어 포함) Object-> Table로 매핑해주는 기술

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
    private int id;

    @Column(nullable = false, length = 20)
    private String username;

    @Column(nullable = false, length = 100) //for hashing
    private String password;

    @Column(nullable = false, length = 40)
    private String email;

    @ColumnDefault("'user'")
    private String role; //enum을 쓰는 게 좋다. //admin user manager

    @CreationTimestamp // 시간 자동 입력
    private Timestamp createDate;
}
