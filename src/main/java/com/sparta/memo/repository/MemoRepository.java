package com.sparta.memo.repository;

import com.sparta.memo.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Memo, Long> { // JpaRepository 덕분에 @Repository 선언 안해도 자동으로 Bean으로 취급
}