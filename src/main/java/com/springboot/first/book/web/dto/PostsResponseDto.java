package com.springboot.first.book.web.dto;

import com.springboot.first.book.domain.posts.Posts;
import lombok.Getter;

@Getter
public class PostsResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;

    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }
}
/*
* Entity의 필드 중 일부만 사용 > 굳이 모든 필드를 가진 생성사 필요 X > Entity를 받아 처리
* */