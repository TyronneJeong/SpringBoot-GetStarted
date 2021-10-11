package SpringBoot.SpringBootGetStarted;

import SpringBoot.SpringBootGetStarted.repository.MemberRepository;
import SpringBoot.SpringBootGetStarted.service.MemberService;
import SpringBoot.SpringBootGetStarted.aop.TimeTraceApp;
import SpringBoot.SpringBootGetStarted.repository.*;
import SpringBoot.SpringBootGetStarted.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    // [Configuration]
    // 스프링 빈을 수동으로 등록 시키는 방법
    // @Configuration 어노테이션으로 지정된 클래스를 통해
    // 컨테이너 로드시 스프링빈으로 상주 시킬 클래스 명들을 서술 할 수 있다.

    // [Bean]
    // @Bean 은 스프링빈으로 등록 시킬 클래스(Bean)을 지정하기 위한 어노테이션이다.

    // [Where to use]
    // 가상의 시나리오 : 현재 DB 시스템이 구축 중이며, 임시로 적용가능한 메모리 저장소를
    // 이용하여 개발 중이였다. 하지만 현재는 DB 시스템이 구축 되어 사용가능 하며 기존 소스코드의
    // 수정을 원하지 않는 상황이다.
    // 이때, 스프링빈으로 호출되는 repository 의 이름을 그대로 두고
    // return 되는 인스턴스만 DB-Operator 로 교체하는 식으로 적용 가능하다.

    // [JDBC, JDBCTemplate 사용시 호출]
    // ************************************************************************
    // private DataSource dataSource;
    // @Autowired
    // public SpringConfig(DataSource dataSource) {
    //    this.dataSource = dataSource;
    // }
    // ************************************************************************

    // [JPA 사용시 호출]
    // ************************************************************************
    // @PersistenceContext
    // private EntityManager em;
    // @Autowired
    // public SpringConfig(EntityManager em) {
    //    this.em = em;
    // }
    // ************************************************************************

    // [SPring Data JPA 사용시 호출]
    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Bean
    public MemberService memberService(){
        //return new MemberService(memberRepository()); // JDBC, JDBCTemplate, JPA 사용시 해당 항목을 호출
        return new MemberService(memberRepository); // Spring Data JPA 사용시 해당 항목을 호출
    }

    // Spring Data JPA 호출시 아래 항목 필요 없음
    //@Bean
    //public MemberRepository memberRepository(){
        // return new MemoryMemberRepository(); // In-Memory 객체
        // return new JDBCMemberRepository(dataSource); // JDBC 를 직접 호출 하여 컨트롤
        // return new JDBCTemplateMemberRepository(dataSource); // JDBCTemplate 를 사용한 호출
        // return new JPAMemberRepository(em); // JPA 를 사용한 호출
        // return new JPAMemberRepository(em); // JPA 를 사용한 호출
    //}

}
