package hellojpa;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) // 상속 관계 매핑 전략
@DiscriminatorColumn // DTYPE 컬럼 자동 생성: 이 클래스를 상속받는 엔티티들을 구별해주는
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ITEM_ID")
    private Long id;

    private String name;
    private int price;
}
