package hellojpa;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity //@Entity는 JPA에서 관리하는 엔티티임을 의미
//@Table // 엔티티를 원하는 테이블과 매핑
//@SequenceGenerator(name = "member_seq_generator", sequenceName = "member_seq") // 기본키 시퀀스 매핑 시 옵션
public class Member extends BaseEntity{

    @Id // JPA 에게 주요키 임을 알리는 어노테이션
//    @GeneratedValue(strategy = GenerationType.AUTO) // 자동 생성, IDENTITY,SEQUENCE,TABLE 중 알아서 선택
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB에 자동 생성을 위임한다.
//    @GeneratedValue(strategy = GenerationType.SEQUENCE) // DB에 자동 생성을 위임한다.
//    @GeneratedValue(strategy = GenerationType.TABLE) // DB에 자동 생성을 위임한다.
    @Column(name = "MEMBER_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 회원 일 - 팀 다 관계
    @JoinColumn(name = "TEAM_ID", insertable = false, updatable = false) // 조인 할 컬럼 이름 설정
    private Team team;

    @Column(name = "USERNAME")
    private String username;

    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;

    @OneToMany(mappedBy = "member")
    private List<Product> memberProducts = new ArrayList<>();

    @Embedded
    private Period workPeriod;

    @Embedded
    @AttributeOverrides ( {@AttributeOverride(name = "city",
            column = @Column(name = "HOME_CITY")),
            @AttributeOverride(name = "street",
                    column = @Column(name = "HOME_STREET")),
            @AttributeOverride(name = "zipcode",
                    column = @Column(name = "HOME_ZIPCODE")) })
    private Address homeAddress;

    @Embedded
    @AttributeOverrides ( {@AttributeOverride(name = "city",
            column = @Column(name = "WORK_CITY")),
            @AttributeOverride(name = "street",
                    column = @Column(name = "WORK_STREET")),
            @AttributeOverride(name = "zipcode",
                    column = @Column(name = "WORK_ZIPCODE")) })
    private Address workAddress;

    /*연관관계 편의 메소드*/
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void changeTeam(Team team){
        this.team = team;
        team.getMembers().add(this); // 양방향 연관관계 수정 시 양쪽 수정
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Period getWorkPeriod() {
        return workPeriod;
    }

    public void setWorkPeriod(Period workPeriod) {
        this.workPeriod = workPeriod;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }
}
