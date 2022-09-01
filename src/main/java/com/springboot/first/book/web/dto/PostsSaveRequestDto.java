package com.springboot.first.book.web.dto;

import com.springboot.first.book.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String author;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Posts toEntity(){
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}
/*
* Entity 클래스와 유사한 형태임에도 Entity 클래스를 Request/Response 클래스로 사용하면 안되기 때문에 Dto 클래스를 따로 생성함
* 이유: Entity 클래스는 데이터베이스와 맞닿은 핵심 클래스. Entity 클래스를 기준으로 테이블이 생성되고 스키마가 변경됨
*       Request/Response용 Dto는 View를 위한 클래스라 자주 변경되는데, Entity 클래스는 여러 서비스 클래스나 비즈니스 로직들에 영향을
*       미치기 때문에 자주 변경되면 위험함.
* */