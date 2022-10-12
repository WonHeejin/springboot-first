package com.springboot.first.book.domain.user;

import com.springboot.first.book.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    //@Enumerated: jpa로 데이터베이스로 저장할때 enum값을 어떤 형태로 저장할지 결정.
    // 기본적으로는 int. 숫자로 저장되면 그 값이 무슨 코드를 의미하는지 알기 어려움으로 string으로 저장
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String name, String email, String picture, Role role) {

        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }

    public  String getRoleKey() {
        return this.role.getKey();
    }
}
