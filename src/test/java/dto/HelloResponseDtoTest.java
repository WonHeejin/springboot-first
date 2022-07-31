package dto;

import com.springboot.first.book.web.dto.HelloResponseDto;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloResponseDtoTest {

    @Test
    public void testResponseDto(){
        //given
        String name = "test";
        int amount = 1000;

        //when
        HelloResponseDto dto = new HelloResponseDto(name,amount);

        //then
        assertThat(dto.getName()).isEqualTo(name); //assertThat : assertj라는 테스트 검중 라이브러리의 메소드. 검증하고싶은 대상을 메소드 인자로 받음.
        assertThat(dto.getAmount()).isEqualTo(amount); //isEqualTo : assertThat의 체이닝된 메소드. 동등비교.

    }
}
/*
 * 롬복 테스트 오류
 * lombok error: variable name not initialized in the default constructor private final String name;
 * gradle ver 5.x 이상부터는 의존성 주입 방법이 달라짐
 */
