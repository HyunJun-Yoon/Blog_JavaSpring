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

    @Transactional(readOnly = true)
    public Page<Board> list(Pageable pageable){
       return boardRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Board detail(int id){
        return boardRepository.findById(id)
                .orElseThrow(()->{
                    return new IllegalArgumentException("Failed Cannot found the id");
                });
    }

    @Transactional
    public void delete(int id){
        boardRepository.deleteById(id);
    }

    @Transactional
    public void edit(int id, Board requestBoard){
        Board board = boardRepository.findById(id)
                .orElseThrow(()->{
                    return new IllegalArgumentException("Failed Cannot found the id");
                });
        board.setTitle(requestBoard.getTitle());
        board.setContent(requestBoard.getContent());
        //해당 함수 종료시 즉 service가 종료될 때 transaction도 종료된다. 이 때 dirty checking 자동으로 업데이트 됨 db flush
    }

}
