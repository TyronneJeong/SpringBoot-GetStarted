package SpringBoot.SpringBootGetStarted.repository;

import SpringBoot.SpringBootGetStarted.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    // Spring Data JPA 가 해당 인터페이스의 구현체를 자동 생성 해준다.
    // findByName 으로 생성 되는 쿼리는 "SELECT m FROM Member M WHERE m.NAME = ?" 와 같다.
    // findBy{something} 형태로 네이밍 만으로 컨디션 속성을 정의할 수 있다.
    // ex) Optional<Member> findByNameAndId(String name, Long id)
    @Override
    Optional<Member> findByName(String name);
}
