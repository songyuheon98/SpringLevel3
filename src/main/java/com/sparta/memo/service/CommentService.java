package com.sparta.memo.service;

import com.sparta.memo.dto.CommentRequestDto;
import com.sparta.memo.dto.CommentResponseDto;
import com.sparta.memo.entity.Comment;
import com.sparta.memo.entity.UserRoleEnum;
import com.sparta.memo.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service// 빈으로 등록
public class CommentService {

    private final CommentRepository commentRepository; // final은 무조건 생성자로 주입
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public CommentResponseDto createComment(CommentRequestDto requestDto, String username) {
        // RequestDto -> Entity
        Comment comment = new Comment(requestDto, username);
        // DB 저장
        Comment saveComment = commentRepository.save(comment);
        // Entity -> ResponseDto
        CommentResponseDto commentResponseDto = new CommentResponseDto(saveComment);
        return commentResponseDto;
    }
//
//    public List<MemoResponseDto> getMemos() {
//        // DB 조회
//        return memoRepository.findAllByOrderByCreatedAtDesc().stream().map(MemoResponseDto::fromMemo).toList();// 메모를 리스트 타입으로 반환
//    }
//
//    public MemoResponseDto getOneMemo(Long id) {
//        // DB 조회
//        Memo memo = memoRepository.findMemoById(id);
//
//        return MemoResponseDto.fromMemo(memo);
//    }
//
    @Transactional // updateMemo는 따로 Transactional 되어있지 않아 해줘야함
    public CommentResponseDto updateComment(Long id, String contents, String username, UserRoleEnum role) {
        Comment comment = findComment(id);
        if(role.getAuthority().equals("ROLE_ADMIN")|| comment.getUsername().equals(username) )
            comment.update(contents); // update는 memo 클래스에서 만든 것
        else
            throw new IllegalArgumentException("당신에겐 글을 수정할 권한이 없습니다 >.< !!");

        return new CommentResponseDto().fromComment(comment);
    }
//
//    public ResponseEntity<String>  deleteMemo(Long id, String username, UserRoleEnum role) {
//        Memo memo = findMemo(id);
//        if(role.getAuthority().equals("ROLE_ADMIN")|| memo.getUsername().equals(username))
//            memoRepository.delete(memo);
//        else
//            return ResponseEntity.ok("{\"msg\": \"삭제 실패\", \"statusCode\": 444}");
//        return ResponseEntity.ok("{\"msg\": \"삭제 성공\", \"statusCode\": 200}");
//
//    }
//
    private Comment findComment(Long id) { // 메모 찾기
        return commentRepository.findById(id).orElseThrow(() ->  // null 시 오류 메시지 출력
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다.")
        );
    }



}