package com.springboot.first.book.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {

    // SpringDataJpa에서 제공하지 않는 메소드는 @Query로 작성
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();
}
/*
* ibatis나 Mybatis 등에서 Dao라고 불리는 DB Layer 접근자
* JPA에선 Repository라고 부르며 인터페이스로 생성
* JpaRepository<Entity 클래스, PK 타입>를 상속하면 기본적인 CRUD 메소드 자동 생성됨
* Entity 클래스와 기본 Entity Repository는 함께 위치해야 함 -- Entity 클래스는 기본 Repository 없이는 제대로 역할을 할 수 없음
* */