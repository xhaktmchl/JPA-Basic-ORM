package hellojpa;

import javax.persistence.*;

@Entity
public class Product extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PRODUCT_ID")
    private Long id;

    private String name;


}
