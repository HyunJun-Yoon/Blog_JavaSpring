package com.cos.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;


//ORM  -> JAVA(다른 언어 포함) Object-> Table로 매핑해주는 기술

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
//@DynamicInsert insert시에 null인 필드를 제외시켜준다
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
    private int id;

    @Column(nullable = false, length = 20, unique=true)
    private String username;

    @Column(nullable = false, length = 100) //for hashing
    private String password;

    @Column(nullable = false, length = 40)
    private String email;

    //@ColumnDefault("'user'")
    //DB는 Role이란 타입이 없어서 annotation을 달아준다.
    @Enumerated(EnumType.STRING)
    private Role role; //enum을 쓰는 게 좋다. //ADMIN USER

    @CreationTimestamp // 시간 자동 입력
    private Timestamp createDate;
}
