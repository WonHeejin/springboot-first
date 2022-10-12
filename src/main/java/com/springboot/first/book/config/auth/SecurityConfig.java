package com.springboot.first.book.config.auth;

import com.springboot.first.book.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable() // h2-console 화면을 사용하기 위해 해당 옵션들 disable 처리
                .and()
                     //authorizeRequests: url별 권한 관리 설정하는 옵션의 시작점.
                    .authorizeRequests()
                        //antMatchers: 권한 관리 대상을 지정하는 옵션. url, htttp 메소드별로 관리 가능
                        //permitAll(): 전체 열람 권한 부여
                        .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                        //USER 권한을 가진 사람만 열람 가능
                        .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                        //anyRequest(): 설정된 값들 이외 나머지 url
                        //authenticated(): 인증된 사용자들(로그인한 사용자)에게만 허용
                        .anyRequest().authenticated()
                .and()
                    //로그아웃 성공시 "/" 주소로 이동
                    .logout().logoutSuccessUrl("/")
                .and()
                    //OAuth2 로그인 기능에 대한 설정 진입점
                    .oauth2Login()
                        //userInfoEndpint(): 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당
                        .userInfoEndpoint()
                            //userService: 소셜 로그인 성공 시 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능 구현
                            .userService(customOAuth2UserService);
    }
}
