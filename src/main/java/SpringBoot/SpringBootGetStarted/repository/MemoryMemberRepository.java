package SpringBoot.SpringBootGetStarted.repository;

import SpringBoot.SpringBootGetStarted.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

//@Repository - 스프링빈 수동 호출로 대체
public class MemoryMemberRepository implements MemberRepository {

    // [HashMap]
    // 공유 변수는 concurrency(동시성) 병렬처리에서 이중 참조되는 경우의 문제 때문에
    // ConcurrentMap, ConcurrentHashMap 을 사용해 주어야 한다.
    private static Map<Long, Member> store = new HashMap<>();

    // [Long]
    // 역시 동시성 제어 문제로 인해 wrapping class 인 AtomicLong 을 사용해 주어야 한다.
    // AtomicLong 사용시 synchronized 없이 멀티 스레드에서 동시성을 보장할 수 있게 된다.
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    // 저장된 데이터 삭제처리
    public void clearStore(){
        store.clear();
    }

}
