package com.cos.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    private int id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob // 대용량 데이터
    private String content; //summernote library를 디자인을 위해 사용할건데 <html>tag가 들어가 용량이 늘어난다.

    @ColumnDefault("0")
    private int count; //조회수

    @ManyToOne //Many = board, User = One
    @JoinColumn(name="userId")
    private User user; //DB는 object를 저장할 수 없어 FK를 사용하지만 JAVA는 object를 저장할 수 있다. DB에는 User 객체를 참조해 FK를 만든다

    @CreationTimestamp
    private Timestamp createDate;


}
