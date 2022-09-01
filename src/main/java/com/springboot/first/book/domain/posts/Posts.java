package com.springboot.first.book.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
/*테이블과 링크될 클래스임을 나타냄. 기본값으로 클래스의 카멜케이스 이름을 언더스코어 네이밍(_)으로 테이블 이름을 매칭함.
* ex)SalesManager.java > sales_manager table*/
@Entity
public class Posts {
    @Id //해당 테이블의 PK필드
    //스프링부트 2.0에서는 GenerationType.IDENTITY 옵션을 추가해야만 auto_increment가 됨.
    // auto_increment : 값이 삽입될때마다 1씩 증가시켜줌. mysql - auto_increment == oracle - sequence
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Column : 테이블 칼럼을 나타내나 굳이 선언하지 않더라도 해당 클래스의 필드는 모드 칼림이 됨.
    // 기본값 외에 추가로 변경이 필요한 욥션이 있을 경우 사용.
    // 문자열의 기본값은 VARCHAR(225)
    @Column(length = 500, nullable = false)
    private String title;
    @Column(columnDefinition ="TEXT", nullable = false)
    private String content;

    private String author;

    @Builder // 해당 클래스의 빌더 패턴 클래스를 생성. 생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함.
    public Posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
/*-----기본적인 구조-----
* setter를 사용하지 않고 생성자를 통해서 값을 채운 후 DB에 삽입
* 값 변경이 필요한 경우 해당 이벤트에 맞는 public 메소드를 호출하여 변경
* >>>> 지금 채워야 할 필드가 무엇인지 정확히 지정할 수 X >>> 코드를 실행하기 전까지 문제 찾기 어려움
* -----@Builder 사용하기-----
* 어느 필드에 어떤 값을 채워야할지 명학하게 인지할 수 있음 */