package com.cos.blog;

import com.cos.blog.model.Comment;
import org.junit.Test;

public class CommentObjectTest {

    @Test
    public void toStringTest(){
        Comment comment = Comment.builder()
                .id(1)
                .user(null)
                .board(null)
                .content("HI")
                .build();

        System.out.println(comment);
    }
}

