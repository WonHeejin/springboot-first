package com.springboot.first.book.config;

import com.springboot.first.book.config.auth.LoginUserArgumentationResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
    //생성한 어노테이션이 스프링에서 인식될 수 있도록 WebMvcConfigurer에 추가하는 작업
    private final LoginUserArgumentationResolver loginUserArgumentationResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginUserArgumentationResolver);
    }
}
