package com.cos.blog.service;

import com.cos.blog.dto.CommentSaveRequestDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.Comment;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.CommentRepository;
import com.cos.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




//IoC
@Service
@RequiredArgsConstructor //another way of DI instead of using @Autowired
public class BoardService {
      private final BoardRepository boardRepository;
      private final CommentRepository commentRepository;

//    @Autowired
//    private BoardRepository boardRepository;
//
//
//    @Autowired
//    private CommentRepository commentRepository;
//

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
    @Transactional
    public void writeComment(CommentSaveRequestDto commentSaveRequestDto){ //User user, int boardId, Comment requestComment
//        User user = userRepository.findById(commentSaveRequestDto.getUserId())
//                .orElseThrow(()->{
//                    return new IllegalArgumentException("Failed to write a comment: cannot found user id");
//                }); //영속화 완료
//
//        Board board = boardRepository.findById(commentSaveRequestDto.getBoardId())
//                .orElseThrow(()->{
//                    return new IllegalArgumentException("Failed to write a comment: cannot found board id");
//                }); //영속화 완료
//
//        Comment comment = new Comment();
//        comment.update(user, board, commentSaveRequestDto.getContent());
////        requestComment.setUser(user);
////        requestComment.setBoard(board);
//        commentRepository.save(comment);
        commentRepository.commentSave(commentSaveRequestDto.getUserId(), commentSaveRequestDto.getBoardId(), commentSaveRequestDto.getContent());
    }

    @Transactional
    public void deleteComment(int commentId){
        commentRepository.deleteById(commentId);
    }
}
