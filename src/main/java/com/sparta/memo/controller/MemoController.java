package com.sparta.memo.controller;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.entity.Memo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MemoController {

    private final JdbcTemplate jdbcTemplate;

    public MemoController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto) { // 3번 요청 완성
        // RequestDto -> Entity
        Memo memo = new Memo(requestDto);

        // DB 저장
        KeyHolder keyHolder = new GeneratedKeyHolder(); // 기본 키를 반환받기 위한 객체

        String sql = "INSERT INTO gallery (user_pw, username, title, contents) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update( con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);

                    preparedStatement.setString(1, memo.getUser_pw());
                    preparedStatement.setString(2, memo.getUsername());
                    preparedStatement.setString(3, memo.getTitle());
                    preparedStatement.setString(4, memo.getContents());
                    return preparedStatement;
                },
                keyHolder);

        // DB Insert 후 받아온 기본키 확인
        Long id = keyHolder.getKey().longValue();
        memo.setId(id);

        // Entity -> ResponseDto
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);

        return memoResponseDto;
    }

    @GetMapping("/memos")
    public List<MemoResponseDto> getMemos() { // 2번 요청 완성
        // DB 조회
        String sql = "SELECT title, username, contents, updateAt FROM gallery ORDER BY updateAt DESC";//order by로 내림차순

        return jdbcTemplate.query(sql, new RowMapper<MemoResponseDto>() {
            @Override
            public MemoResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                // SQL 의 결과로 받아온 Memo 데이터들을 MemoResponseDto 타입으로 변환해줄 메서드
                String title = rs.getString("title");
                String username = rs.getString("username");
                String contents = rs.getString("contents");
                Timestamp updateAt = rs.getTimestamp("updateAt");
                return new MemoResponseDto(title, username, contents, updateAt);
            }
        });
    }

    @GetMapping("/memos/{id}")
    public MemoResponseDto getOneMemo(@PathVariable Long id) { // 4번 요청 완성
        // DB 조회
        Memo memo = findById(id);

        if (memo != null) {
            String sql = "SELECT title, username, updateAt, contents FROM gallery WHERE id = ?";// 해당 아이디 값으로 된 게시물 조회
            return jdbcTemplate.queryForObject(sql, new RowMapper<MemoResponseDto>() {
                @Override
                public MemoResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                    // SQL 의 결과로 받아온 Memo 데이터들을 MemoResponseDto 타입으로 변환해줄 메서드
                    String title = rs.getString("title");
                    String username = rs.getString("username");
                    Timestamp updateAt = rs.getTimestamp("updateAt");
                    String contents = rs.getString("contents");
                    return new MemoResponseDto(title, username, contents, updateAt);
                }
            }, id);
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }

    @PutMapping("/memos/{id}")
    public MemoResponseDto updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) { // 5번 요청
        // 해당 메모가 DB에 존재하는지 확인
        Memo memo = findById(id);
        String getPasswordQuery = "SELECT user_pw FROM gallery WHERE id = ?";
        String actualPassword = jdbcTemplate.queryForObject(getPasswordQuery, String.class, id); // 비밀번호 저장

        if(memo != null && actualPassword.equals(requestDto.getUser_pw())) {
            // memo 내용 수정
            String sql = "UPDATE gallery SET title = ?, username = ?, contents = ? WHERE id = ?";
            jdbcTemplate.update(sql, requestDto.getTitle(), requestDto.getUsername(), requestDto.getContents(), id);

            Memo updatedMemo = findById(id);

            // Entity -> ResponseDto
            return new MemoResponseDto(updatedMemo); // 게시글을 반환
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }

    @DeleteMapping("/memos/{id}")
    public String deleteMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) { // 6번 요청
        // 해당 메모가 DB에 존재하는지 확인
        Memo memo = findById(id);
        String getPasswordQuery = "SELECT user_pw FROM gallery WHERE id = ?"; // 5번과 동일한 과정
        String actualPassword = jdbcTemplate.queryForObject(getPasswordQuery, String.class, id);
        if(memo != null && actualPassword.equals(requestDto.getUser_pw())) {
            // memo 삭제
            String sql = "DELETE FROM gallery WHERE id = ?";
            jdbcTemplate.update(sql, id);

            return "삭제 성공";
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }

    private Memo findById(Long id) {
        // DB 조회
        String sql = "SELECT * FROM gallery WHERE id = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if(resultSet.next()) {
                Memo memo = new Memo();
                memo.setUser_pw(resultSet.getString("user_pw"));
                memo.setTitle(resultSet.getString("title"));
                memo.setUsername(resultSet.getString("username"));
                memo.setContents(resultSet.getString("contents"));
                memo.setUpdatedAt(resultSet.getTimestamp("updateAt"));
                return memo;
            } else {
                return null;
            }
        }, id);
    }
}