package SpringBoot.SpringBootGetStarted.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // [Static Page] vs [Root Page]
    // 1. 스프링 컨테이너내 관련 Componenet 를 검색한다.
    // 2. 컨테이너에 등록된 스프링 Bean 이 없는 경우 Static Page 로 이동한다.

    @GetMapping("/")
    public String homr(){
        return "home";
    }

}
