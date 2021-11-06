package SpringBoot.SpringBootGetStarted.controller;

import SpringBoot.SpringBootGetStarted.domain.Member;
import SpringBoot.SpringBootGetStarted.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

// [Component Scan]
// @Components 어노테이션의 경우 컨테이너가 부팅될때
// @Components 어노테이션으로 마킹된 클래스에 대해서는
// 스프링빈 형태로 호출 가능 토록 컨테이너에 등록 시키는 역할을 한다. (Singleotne)
// @Controller 태그는 [@Componenet]의 상속 객체로
// @Contoller, @Service, @Repository 모두 동일하다.
// *Scope : main.class 이하(@SpringBookApplication)의 호출 객체에 대해서만 스캐닝이 이루어 진다.

// [스프링빈을 직접 자바코드로 서술하는 형태]
// SpringConfig.java 에 별도 기술
// @Configuration 어노테이션을 통해 수동으로 등록 시킬 Bean 을 정의할 수 있다.
// @Controller 의 경우 wireing 이 시작되는 클래스로 @Configuraion 내 기술은 불가능 하다.

@Controller
public class MemberContoller {
    {
        // [Dependency Injection 의 3가지 유형]
        // 1. 생성자 주입 (권장)
        //  ㄴ private 맴버를 선언 후 생성자를 @Autowired 로 바인딩 하는 방식
        // 2. 필드주입
        //  ㄴ 맴버 자체를 @Autowired 로 지정 하는 방식
        // 3. Setter 주입
        //  ㄴ public setMethodName 형식의 메소드를 통해 컨트롤러를 노충시키는 방식
    }

    // [Controller -> Service]
    // 컨트롤러는 서비스를 통해서만 정상 적인 기능을 할 수 있으므로 이를 의존성이 존재한다고 표현한다.
    // [Service]의 경우 해당 컨트롤러에서 뿐만 아니라 다양한 컨트롤러에서도
    // 호출 가능 하기에 인스턴스를 반복적으로 생성 하기 보다 하나의 SIngletone 을
    // 유지하는 것이 유리하므로 아래와 같이 자료형을 선언 하지는 않는다.
    // private final MemberService memberService = new MemberService();

    private final MemberService memberService;

    // [Autowired]
    // Autowired 는 컨테이너에 존재하는 bean 객체중에 해당 서비스 bean 을 찾아 연결 시켜준다.
    // wireing 하기 위해서는 [Service] 클래스에도 스프링 어노테이션 @Service 을 작성 해 주어야 한다.
    @Autowired
    public MemberContoller(MemberService memberService) {

        // [Dependency Injection]
        // 스프링 빈으로 존재하는 Service 를 해당 Controller 의 함수 호출에 대입 시켜주었다.
        // 이를 Dependency Injection 이라고 한다.
        this.memberService = memberService;
        System.out.println("memberService " + memberService.getClass()); // 실제 호출 클래스를 확인
    }

    @GetMapping("/members/new")
    public String createForm(){
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}