package com.sparta.memo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // 하위 패키지 모두 찾아서 @Componet들 Bean으로 등록
public class MemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemoApplication.class, args);
	}

}
