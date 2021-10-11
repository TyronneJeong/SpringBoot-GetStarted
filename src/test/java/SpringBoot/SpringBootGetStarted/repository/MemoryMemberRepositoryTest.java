package SpringBoot.SpringBootGetStarted.repository;

import SpringBoot.SpringBootGetStarted.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    // [Unit Test]
    // 테스트 케이스의 순서는 보장되지 않는다.

    @AfterEach
    public void afterEach(){
        // 각 메서드 실행 후 처리될 후처리 함수
        repository.clearStore();
    }

    @Test
    public void save(){
        Member member = new Member();
        member.setName("spring");

        repository.save(member);
        Member result = repository.findById(member.getId()).get();

        // jupiter 가 제공하는 assert
        // import org.junit.jupiter.api.Assertions;
        // Assertions.assertEquals(result, member);

        // alt + Enter 이용하여 해당 클래스 import 구조를 Assertions 객체 까지로 정의하여 호출 길이를 줄일 수 있음.
        assertThat(member).isEqualTo(result); // 보다 편리한 비교도구

    }


    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        // member1 과 같이 동일 패턴이 반복 사용되는 경우
        // [shift + F6] 을 눌러 동일 변수명을 한번에 수정 할 수 있다.
        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();
        assertThat(result).isEqualTo(member1);
    }


    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();
        assertThat(result.size()).isEqualTo(2);
    }
}
