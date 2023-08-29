package com.sparta.memo.repository;

import com.sparta.memo.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> { // JpaRepository 덕분에 @Repository 선언 안해도 자동으로 Bean으로 취급

    List<Memo> findAllByOrderByModifiedAtDesc(); // sql을 함수형식으로 치면 자동으로 구현, JpaRepository가 해줌, 하지만 선언을 해줘야 다른 클래스에서 이용 가능
    List<Memo> findAllByContentsContainsOrderByModifiedAtDesc(String keyword);
}