package com.cos.blog.repository;

import com.cos.blog.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;


public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Modifying
    @Query(value = "INSERT INTO comment(userId, boardId, content, createDate) VALUES(?1, ?2, ?3, now())", nativeQuery = true)
    int commentSave(int userId, int boardId, String content); //jdbc returns number of updated rows when it completed any modification.
}
