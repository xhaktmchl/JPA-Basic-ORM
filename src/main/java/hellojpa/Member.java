package hellojpa;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity //@Entity는 JPA에서 관리하는 엔티티임을 의미
//@Table // 엔티티를 원하는 테이블과 매핑
public class Member {

    @Id // JPA 에게 주요키 임을 알리는 어노테이션
    private Long id;

    @Column(name="name", nullable = true, length = 50, unique = true) // DDL 생성기능:테이블 컬럼에 옵션지정 가능
    private String userName;

    private Integer age;

    @Enumerated(EnumType.STRING) // enum 타입 매핑, 속성: EnumType.STRING enum타입의 이름을 사용
    private RoleType roleType;

    @Lob // 길이 제한 없는 문자열 매핑
    private String description;

    @Temporal(TemporalType.TIMESTAMP) // 날짜 타입 매핑
    private Date createAt;

    @Temporal(TemporalType.TIMESTAMP) // 날짜 타입 매핑
    private Date updateAt;

    // 최신 날짜타입 LocalDate,LocalDateTime은 @Temporal 필요 없음
    private LocalDate testLocalDate;
    private LocalDateTime testLocalDateTime;

    @Transient // db에 미핑 안시킬 것
    private int tmp;

    // getter, setter
    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getName(){
        return userName;
    }

    public void setName(String userName){
        this.userName= userName;
    }


}
