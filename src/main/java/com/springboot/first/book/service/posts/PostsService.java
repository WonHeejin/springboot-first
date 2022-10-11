package com.springboot.first.book.service.posts;

import com.springboot.first.book.domain.posts.Posts;
import com.springboot.first.book.domain.posts.PostsRepository;
import com.springboot.first.book.web.dto.PostsListResponseDto;
import com.springboot.first.book.web.dto.PostsResponseDto;
import com.springboot.first.book.web.dto.PostsSaveRequestDto;
import com.springboot.first.book.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }
    /*
    * update 기능에서 데이터베이스에 쿼리를 날리는 부분이 없는 이유
    * JPA의 엔티티 매니저가 활성화된 상태로(Spring Data Jpa를 쓴다면 기본 옵션) 트랜잭션 안에서 데이터베이스의 데이터를 가져오면
    * 영속성 컨텍스트가 유지됨
    * 영속성 컨텍스트: 엔티티를 영구 저장하는 환경. 일종의 논리적 개념
    * 영속성 컨텍스트가 유지된 상태에서 해당 데이터의 값을 변경하면 트랜잭션이 끝나는 시점에 해당 테이블에 변경분을 반영함.(= 더티 체킹)
    * */
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        //postsRepository 결과로 넘어온 Posts의 Stream을 map을 통해 PostsListResponseDto로 변환 -> Lists로 변환
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)// .map(posts -> new PostsListResponseDto(posts))와 동일
                .collect(Collectors.toList());
    }
}
