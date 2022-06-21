package hellojpa;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Member {

    @Id // JPA 에게 주요키 임을 알리는 어노테이션
    private Long id;
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
