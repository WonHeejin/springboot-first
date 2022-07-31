package com.springboot.first.book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication >> 스프링 부트의 자동 설정, 스프링Bean 읽기와 생성을 모두 자동으로 설정.
//이 어노테이션이 있는 위치부터 설정을 읽어가기 때문에 이 클래스는 항상 프로젝트 최상단에 위치해야함.
@SpringBootApplication
public class Application {
    public static void main(String[] args){
        // 내장 WAS 실행 >> 서버에 톰캣 설치 필요X, 스프링 부트로 만들어진 Jar 파일로 실행하면 됨.
        SpringApplication.run(Application.class, args);
    }
}
