package SpringBoot.SpringBootGetStarted.repository;

import SpringBoot.SpringBootGetStarted.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id);  // 리턴 값이 null인 경우 이를 optinal 처리하여 null 을 방지한다.
    Optional<Member> findByName(String name);    // 리턴 값이 null인 경우 이를 optinal 처리하여 null 을 방지한다.
    List<Member> findAll();
}