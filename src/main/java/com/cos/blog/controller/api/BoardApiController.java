package com.cos.blog.controller.api;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.CommentSaveRequestDto;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.Comment;
import com.cos.blog.model.Role;
import com.cos.blog.model.User;
import com.cos.blog.repository.CommentRepository;
import com.cos.blog.service.BoardService;
import com.cos.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class BoardApiController {

    @Autowired
    private BoardService boardService;

    @PostMapping("/api/board")
    public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principalDetail){
        boardService.write(board, principalDetail.getUser());
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }


    @DeleteMapping("/api/board/{id}")
    public ResponseDto<Integer> deleteById(@PathVariable int id){
        boardService.delete(id);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @PutMapping("/api/board/{id}")
    public ResponseDto<Integer> update(@PathVariable int id, @RequestBody Board board){
        boardService.edit(id, board);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    //데이터를 받을 때 컨트롤러에서 dto를 만들어서 받는게 좋다.
    @PostMapping("/api/board/{boardId}/comment")
    public ResponseDto<Integer> commentSave(@RequestBody CommentSaveRequestDto commentSaveRequestDto ){ //@PathVariable int boardId, @RequestBody Comment comment, @AuthenticationPrincipal PrincipalDetail principalDetail
        boardService.writeComment(commentSaveRequestDto); //principalDetail.getUser(), boardId, comment
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @DeleteMapping("api/board/{boardId}/comment/{commentId}")
    public ResponseDto<Integer> commentDelete(@PathVariable int commentId){
        boardService.deleteComment(commentId);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

}
