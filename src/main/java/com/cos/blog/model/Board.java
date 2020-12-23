package com.cos.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


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

    private int count; //조회수

    @ManyToOne(fetch = FetchType.EAGER) //Many = board, User = One
    @JoinColumn(name="userId")
    private User user; //DB는 object를 저장할 수 없어 FK를 사용하지만 JAVA는 object를 저장할 수 있다. DB에는 User 객체를 참조해 FK를 만든다

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER) //mappedBy 연관관계의 주인이 아니다 (FK가 아니다) DB에 컬럼을 만들지 마세요. FK는 Reply에 boardID로 들어간다.
    //@JoinColumn(name="replyId") FK가 필요없음 Reply ID가 여러개가 한 셀에 들어갈 수 없기 때문
    private List<Reply> reply;

    @CreationTimestamp
    private Timestamp createDate;


}
