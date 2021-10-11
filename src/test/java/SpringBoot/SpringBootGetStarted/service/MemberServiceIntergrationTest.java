package SpringBoot.SpringBootGetStarted.service;

import SpringBoot.SpringBootGetStarted.domain.Member;
import SpringBoot.SpringBootGetStarted.repository.MemberRepository;
import SpringBoot.SpringBootGetStarted.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class MemberServiceIntergrationTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

// [SpringBootTest]
// Autowired 로 클래스 객체를 호출 하였으므로 아래 구문이 필요 없어진다.
//    @BeforeEach
//    public void beforeEach() {
//        memberRepository = new MemoryMemberRepository();
//        memberService = new MemberService(memberRepository); // 외부에서 의존성을 주입하는 케이스
//        // Dependency Injection (D.I)
//    }
//    @AfterEach
//    public void afterEach(){
//        // 각 메서드 실행 후 처리될 후처리 함수
//        memberRepository.clearStore();
//    }

// [Transactional]
// 테스트 종료 후, 트랜잭션은 롤백을 처리된다.

// [Commit]
// 테스트 종료 후, 입력된 값이 커밋 된다.

    @Test
    void join() {
        // given - 주어진 값
        Member member = new Member();
        member.setName("hello");

        // when - 어떤 상황인지
        Long saveId = memberService.join(member);

        // then - 나와야 하는 상황
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    void checkDuplicate(){
        // **************************************************************
        // 전통적인 오류 상황에 대한 예외처리 try ~ catch
        // try {
        //    memberService.join(member2);
        //    fail();
        //} catch (IllegalStateException e){
        //    // catch 된 예외상황 메세지가 우리가 설정한 메세지와 같은지를 확인한다.
        //    assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        // }
        // **************************************************************

        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // when
        memberService.join(member1);

        // Lambda 이후의 작업을 실행 하는데 IllegalStateException 오류가 발생하는지를 검증 ※ 미발생시 오류
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        // 리턴된 e 메세지의 문구가 다음과 같은지를 검증.
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        // then - N/A
    }

}
