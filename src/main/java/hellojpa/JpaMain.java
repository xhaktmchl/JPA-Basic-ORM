package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        // 엔티티매니저 팩토리 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hellojpa");
        // 엔티티매니저 팩토리 -> 엔티티 매니저 생성
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction(); // JPA에서는 트랜섹션이 시작되고 해야 에러가 안나고 인식이 잘 된다.
        tx.begin();

        try{
            // 멤버 객체 생성 및 JPA에 insert 요청
//            Member member = new Member();
//            member.setId(1L);
//            member.setName("hello1");
//
//            em.persist(member); // 테이블에 생성

            // 멤버 조회
            Member findMember = em.find(Member.class,1L);
            System.out.println("멤버 아이디:"+findMember.getId());

            tx.commit(); // 트랜섹션 요청 실행
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();// 엔티티매니저 종료
        }
        emf.close();
    }
}
