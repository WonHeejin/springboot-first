package com.springboot.first.book.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    //머스테치 스타터로 인해 컨트롤러에서 문자열을 반환할 때 앞의 경로와 뒤의 파일 확장자는 자동으로 지정됨
    //앞의 경로: src/main/resources/templates
    //뒤의 파일 확장자: .mustache
    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("post/save")
    public String postsSave() {
        return "post-save";
    }

}
