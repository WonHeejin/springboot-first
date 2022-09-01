package com.springboot.first.book.web;

import com.springboot.first.book.service.posts.PostsService;
import com.springboot.first.book.web.dto.PostsResponseDto;
import com.springboot.first.book.web.dto.PostsSaveRequestDto;
import com.springboot.first.book.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor // 생성자를 직접 안쓰고 롬복 어노테이션을 사용 > 의존성 관계가 변경될 때마다 생상자 코드를 수정해야하는 번거로움 해결
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto){
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id) {
        return postsService.findById(id);
    }
}
