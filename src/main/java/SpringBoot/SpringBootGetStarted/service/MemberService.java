package SpringBoot.SpringBootGetStarted.service;

import SpringBoot.SpringBootGetStarted.domain.Member;
import SpringBoot.SpringBootGetStarted.repository.MemberRepository;
import SpringBoot.SpringBootGetStarted.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//@Service - 스프링빈 수동 호출로 대체
@Transactional // JPA 사용시 필수 지정
public class MemberService {
    // Test Case 호출 방법 : Class 명 선택 후  -> [Ctrl + Shift + T]
    // 테스트할 메서드를 선택 후 생성 버튼 클릭 시 자동 생성 됨

    private final MemberRepository memberRepository;

    // Constructor 지정을 통해 늘 새로운 인스턴스 생성이 아닌 전달 받은 파라미터로 초기화 하도록 지정
    // Repository 역시 AUtowired 로 스프링 빈과 연결 시킨다.
    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입
     */
    public long join(Member member){
        // 동일명의 중복 고객은 입력이 불가능 하다.
        // Ctrl + Alt + v : 함수 호출 이후 리턴 받을 변수 부 를 자동 생성 해주는 단축키
        Optional<Member> result = memberRepository.findByName(member.getName());

        // [Optinal 자료형의 부가 메소드]
        // result.orElseGet() // 값이 있을 경우 꺼내오는 조건 부 접근 자
        // result.ifPresent(m -> { // Null 인 경우 수행될 메소드 지정
        //    throw new IllegalStateException("이미 존재하는 회원입니다.");
        // });

        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 중복회원 검증 로즉
     */
    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 회원 번호 조회
     */
    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }

}
