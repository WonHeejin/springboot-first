package com.springboot.first.book.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) //어노테이션이 생성될 수 있는 위치 - 파라미터로 선언된 객체에서만 사용할 수 있도록 설정
@Retention(RetentionPolicy.RUNTIME) //어노테이션의 라이프사이클 - 런타임동안으로 설정
public @interface LoginUser { //@interface: 이 파일을 어노테이션 클래스로 지정.
    //메소드 인자로 세션값을 바로 받을 수 있도록 해주는 어노테이션 생성
}
