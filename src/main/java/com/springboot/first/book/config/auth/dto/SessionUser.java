package com.springboot.first.book.config.auth.dto;

import com.springboot.first.book.domain.user.User;
import lombok.Getter;

import java.io.Serializable;
//User 클래스와 분리하는 이유: 세션에 저장하기 위해서는 직렬화(Serializable)를 구현해야하는데,
//User 클래스는 엔티티 클래스라 다른 엔티티와 관계가 형성될 수 있고, 자식 클래스까지 직렬화 대상에 포함돼 성능이슈, 부수효과가 나타날 수 있음
//따라서 직렬화 기능을 가진 세션 Dto를 따로 만드는 것이 운영 및 유지보수에 도움이 됨
@Getter
public class SessionUser implements Serializable {
    // 세션에 사용자 정보를 저장하기 위한 dto. 인증된 사용자 정보만 필요함

    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
