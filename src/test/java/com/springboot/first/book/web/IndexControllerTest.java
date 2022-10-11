package com.springboot.first.book.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@RunWith(SpringRunner.class)
public class IndexControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void 메인페이지_로딩() {
        //when
        // getForObject: 첫 번째 파라미터 url(Get 타입)의 응답값을 두 번째 파라미터의 타입으로 리턴해 줌
        //html도 결국 규칙이 있는 문자열이기 때문에 응답값을 string으로 받아서 확인
        String body = this.restTemplate.getForObject("/", String.class);

        //then 해당 문자열이 포함되어있는지 검증
        assertThat(body).contains("스프링 부트 웹 서비스"); // 통과
    }
}
