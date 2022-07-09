package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        // 엔티티매니저 팩토리 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hellojpa");
        // 엔티티매니저 팩토리 -> 엔티티 매니저 생성
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction(); // JPA에서는 트랜섹션이 시작되고 해야 에러가 안나고 인식이 잘 된다.
        tx.begin();

        try{
//            // 멤버 객체 생성 및 JPA에 insert 요청
////            Member member = new Member();
////            member.setId(1L);
////            member.setName("hello1");
////
////            em.persist(member); // 테이블에 생성
//
//            // 멤버 조회
//            Member findMember = em.find(Member.class,1L);
//            System.out.println("멤버 아이디:"+findMember.getId());
//
//            //멤버 수정
//            findMember.setName("수정된 멤버 이름2");
//
//            //멤버 삭제
//            em.remove(findMember);
//
//
//            /*
//            JPQL
//            * */
//            // JPQL 쿼리 작성해서 멤버 리스트 전체조회
//            // 객체지향 쿼리: JPQL쿼리에서 Member는 테이블이 아니라 객체
//            List<Member> result = em.createQuery("select m from Member as m",Member.class)
//                    .setFirstResult(3) // 3번 객체부터
//                    .setMaxResults(8) // 8개 가지 조회
//                    .getResultList();
//
//            //전체 멤버 리스트 출력
//            for(Member member:result){
//                System.out.println("MemberName is : "+member.getName());
//            }
//
//
//            /*영속성 컨텍스트*/
//            //1.비영속
//            Member member = new Member();
//            member.setId(100L);
//            member.setName("이름2");
//
//            //2.영속
//            System.out.println("===Before");
//            em.persist(member); // 1차 캐시에 저장 됨
//            System.out.println("==After");
//
//            em.find(Member.class,100L);// 1차 캐시에서 조회 => 쿼리를 안날리고 바로 조회
//
//            /*영속성 사용 이유*/
//            //1.엔티티 동일성 보장
//            Member member1 = em.find(Member.class, 100L);
//            Member member2 = em.find(Member.class, 100L);
//            System.out.println(member1 == member2); // 같은 객체로 인식식
//
//            //2.트랜젝션 지원하는 쓰기 지연
//            Member member3 = new Member();
//            Member member4 = new Member();
//
//            em.persist(member3);
//            em.persist(member4);
//            //트랜젝션 커밋 전 까지 쓰기 지연 sql에 저장되어 있음
//
//            //3.변경 감지
//            member3.setName("수정된 이름3");// 엔티티 수정을 알아서 감지하고 쿼리 날림
//
////            /*준영속 상태로 만들기*/
////            em.detach(member); // 해당 엔티티만 준영속
////            em.clear(); // 1차캐시에 있는 모든 엔티티 준영속으로
////            em.close(); // 영속 삭태를 종료

//            /*
//            연관관계 없는 엔티티 코딩
//            * */
//            Team team = new Team();
//            team.setName("team1");
//            em.persist(team);
//
//            Member member = new Member();
//            member.setUsername("name1");
//            member.setTeamId(team.getId());
//            em.persist(member);

            /*
            * 단방향 연관관계 설정
            * */
            // 저장
            Team team2 = new Team();
            team2.setName("team2");
            em.persist(team2);

            Member member2= new Member();
            member2.setUsername("name2");
            member2.setTeam(team2); // 단방향 연관관계 설정,
            em.persist(member2);
            //조회
            Member findMember2 = em.find(Member.class,member2.getId());
            System.out.println("연관관계 참조한 팀:"+findMember2.getTeam().getName()); // 멤버에 속한 팀 바로 조회


            // 쓰기지연 sql 저장소에 모든 sql 실행
            tx.commit(); // 트랜섹션 요청 실행
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();// 엔티티매니저 종료
        }
        emf.close();
    }
}
