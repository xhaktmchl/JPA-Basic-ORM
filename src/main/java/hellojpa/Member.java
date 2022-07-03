package hellojpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity //@Entity는 JPA에서 관리하는 엔티티임을 의미
@Table // 엔티티를 원하는 테이블과 매핑
public class Member {

    @Id // JPA 에게 주요키 임을 알리는 어노테이션
    private Long id;

    @Column(name="name", nullable = true, length = 50, unique = true) // DDL 생성기능:테이블 컬럼에 옵션지정 가능
    private String name;

    // getter, setter
    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name= name;
    }


}
