package com.springboot.first.book.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@RunWith >> JUnit5부터는 사용X 쓸 수는 있지만 @ExtendWith로 대체됨 근데 이것도 @SpringBootTest의 메타 어노테이션으로 사용되어 생략 가능함
//출처 https://www.whiteship.me/springboot-no-more-runwith/
//테스트를 진행할 때 JUnit에 내장된 실행자 외 다른 실행자(SpringRunner)를 사용. 스트링부트 테스트와 JUnit 사이에서 연결자 역할을 함.
@RunWith(SpringRunner.class)
//Web(Spring MVC)에 집중할 수 있는 어노테이션 >> 얘를 선언해야 @Controller, @ControllerAdvice 사용 가능 (@Service, @Component, @Repository 등은 사용 불가)
@WebMvcTest(controllers = HelloController.class)
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc; // 웹 API를 테스트할 때 사용. 스프링 MVC 테스트의 시작점. HTTP GET, POST 등에 대한 API 테스트 가능.

    @Test
    public void return_hello() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello")) // MockMvc를 통해 /hello 주소로 HTTP Get 요청
                .andExpect(status().isOk()) // andExpect : mvc.perform의 결과 검증. status() : 200, 400 등의 상태(status) 검증. isOk : 200인지 아닌지 검증
                .andExpect(content().string(hello)); // content() : 응답 본문의 내용을 검증. 컨트롤러에서 리턴하는 hello와 맞는지 검증.
    }

    @Test
    public void return_helloDto() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto")
                .param("name", name) //api 테스트에 사용될 요청 파라미터. sting만 허용됨.
                .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name))) // json 응답값을 필드별로 검증. $를 기준으로 필드명 명시.
                .andExpect(jsonPath("$.amount", is(amount)));
    }
}
/*
* test 오류
* org.junit.platform.commons.PreconditionViolationException: Cannot create Launcher without at least one TestEngine; consider adding an engine implementation JAR to the classpath
* Preference > Build, Execution, Deployment > Build Tools > Gradle > Run test using > Gradle 에서 IntelliJ IDEA로 변경하면 오류 해경
* 출처 https://velog.io/@jinii/%ED%85%8C%EC%8A%A4%ED%8A%B8-%ED%85%8C%EC%8A%A4%ED%8A%B8-%EC%BD%94%EB%93%9C-%EC%86%8C%EA%B0%9C
* */