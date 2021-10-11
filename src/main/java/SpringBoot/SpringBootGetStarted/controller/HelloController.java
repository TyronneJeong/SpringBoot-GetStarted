package SpringBoot.SpringBootGetStarted.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    // ***********************************************************************************
    // [패키지 압축 및 실행]
    // gradlew 를 통해 패키지 압축이 가능하다.
    // 압축된 패키지는 build/libs/{file_name}.jar 형태로 생성된다.
    // java -jar {file_name} 으로 해당 jar 를 구동 시키면 서버 컨테이너를 포함하여 모두 기동 가능하다.

    // [gradle]
    // gradlew build      : 패키지를 빌드한후 jar 로 압축 함
    // gradlew clean       : build 된 패키지를 삭제함
    // gradlew clean build : 기존 생성된 build 패키지를 삭제후 재 빌드 함
    // ***********************************************************************************

    // STEP1. 기초적인 웹 서비스
    @GetMapping("hello") // URL:localhost8080/hello
    public String hello(Model model) {
        // 1. 속성값<K, V> 정의 - data:Hello!!
        model.addAttribute("data", "Hello!!");

        // 2. 리다이렉트 될 페이지 정보
        // viewResolver >> 리다이렉트 되는 파일의 이름
        // default path : resources:template/{ViewName}.html
        // spring-boot-devtools 설치시 HTML 파일의 경우 컴파일후 바로 확인 가능
        return "hello";
    }


    // STEP2. MVC 호출 템플릿
    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam(value="name", required = true) String name, Model model){
        model.addAttribute("name", name);
        return "hello-template";
    }


    // STEP3. 리다이렉션 페이지 호출이 아닌 플레인 스트링 리턴
    // @ResponseBody 지정시 리턴 값은 viewResolver 에 전달되는 것이 아니라 <HttpMessageConverter> 로 전달
    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name){
        return "hello "+name;
    }


    // STEP4. 클래스 객체를 <HttpMessageConverter> 로 전달
    // HttpMessageConverter 는 객체를 [JSON Converter] 와 [String Converter] 중 하나로 처리함
    // [JSON Converter]   - 객체
    // [String Converter] - 문자
    // <K, V> 타입의 경우 JSON 형태로 변환하여 리턴 함
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    // 전달되는 Hello Class
    static class Hello {
        private String name;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }
}