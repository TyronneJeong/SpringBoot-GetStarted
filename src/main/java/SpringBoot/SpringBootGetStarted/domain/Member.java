package SpringBoot.SpringBootGetStarted.domain;

import javax.persistence.*;

@Entity
public class Member {

    // PK 지정 & 자동증가 항목 지정
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
