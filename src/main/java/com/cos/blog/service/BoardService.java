package com.cos.blog.service;

import com.cos.blog.model.Board;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




//IoC
@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional
    public void write(Board board, User user){
        board.setCount(0);
        board.setUser(user);
        boardRepository.save(board);
    }

    public Page<Board> list(Pageable pageable){
       return boardRepository.findAll(pageable);
    }
}
