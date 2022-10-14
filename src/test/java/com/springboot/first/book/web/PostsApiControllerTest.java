package com.springboot.first.book.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.first.book.domain.posts.Posts;
import com.springboot.first.book.domain.posts.PostsRepository;
import com.springboot.first.book.web.dto.PostsSaveRequestDto;
import com.springboot.first.book.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
/* @WebMvcTest는 JPA 기능 작동x >> @SpringBootTest 사용
* WebEnvironment.RANDOM_PORT: 호스트가 사용하지 않는 랜덤 포트를 테스트에 사용하겠다는 뜻*/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    /*REST 방식으로 개발한 API의 Test를 최적화 하기 위해 만들어진 클래스.
    HTTP 요청 후 데이터를 응답 받을 수 있는 템플릿 객체이며 ResponseEntity와 함께 자주 사용된다.
     출처: https://easybrother0103.tistory.com/64  */
    private TestRestTemplate restTemplate;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before //매번 테스트가 시작되기 전에 MockMvc 인스턴스를 생성
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }
    @Autowired
    public PostsRepository postsRepository;

    @After
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER") //인증된 임의의 사용자 생성 (ROLE_USER 권한을 가진 사용자)
    public void Posts_등록된다() throws Exception{
        //given
        String title = "title";
        String content = "content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("author")
                .build();
        String url = "http://localhost:" + port + "/api/v1/posts";
/*
        spring security 적용 전
        //when
        // ResponseEntity: 사용자의 httprequest에 대한 응답 데이터를 포함하는 클래스. httpStatus, httpHeader, httpBody를 포함.
        // postForEntity: POST 요청을 보내고 결과로 헤더에 저장된 URI를 결과로 반환받는다
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);
 */
        //when
        mvc.perform(post(url) //생성된 MockMvc를 통해 api 테스트
                .contentType(MediaType.APPLICATION_JSON_UTF8) //본문 영역은 문자열로 표현 >> ObjectMapper를 이용하여 JOSON으로 변환
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());
        //then
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);

    }

    @Test
    @WithMockUser(roles = "USER") //인증된 임의의 사용자 생성 (ROLE_USER 권한을 가진 사용자)
    public void Posts_수정된다() throws Exception{
        //given
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        Long updateId = savedPosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();
        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;
        HttpEntity<PostsUpdateRequestDto> requestDtoHttpEntity = new HttpEntity<>(requestDto);
/*      spring security 적용 전
        //when
        exchange(): HTTP 헤더를 새로 만들 수 있고 어떤 HTTP 메서드도 사용가능하다
        출처: https://juntcom.tistory.com/141 [쏘니의 개발블로그:티스토리]
        위에서는 post 타입이라 postForEntity 사용. post도 get도 아닌 경우(put, delete)엔 exchange 사용
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestDtoHttpEntity, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);
*/
        //when
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());
        //then
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }
}
