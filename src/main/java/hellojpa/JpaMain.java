package hellojpa;

import org.hibernate.Hibernate;

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


            /*
            * 양방향 연관관계 매핑
            * */
            //저장
            Team team3 = new Team();
            team3.setName("team3");
            em.persist(team3);

            Member member3= new Member();
            member3.setUsername("name3");
            member3.setTeam(team3);
            em.persist(member3);

            em.flush();
            em.clear();
            // 조회
            Member findMember3 = em.find(Member.class,member3.getId());
            List<Member> members = findMember3.getTeam().getMembers(); // 회원이 속한 팀 -> 팀에 속한 모든 멤버들 조회
            for(Member m :members){
                System.out.println("양방향 연관: 팀-> 팀에속한 멤버들"+m.getUsername());
            }


            /*
            * 양방향 연관관계의 주의점
            * */
            // 1.연관관계의 주인을 수정해야  상대 엔티티에도 자동으로 적용된다.
            // Team.members 를 바꿔도 안 됌.
            Member member4= new Member();
            member4.setUsername("name4");
            //member4.setTeam(team4);
            em.persist(member4);

            Team team4 = new Team();
            team4.setName("team4");
            team4.getMembers().add(member4); // 주의: 연관관계 주인이 아니여서 적용 안됌.
            em.persist(team4);

            em.flush();
            em.clear();

            //2.해결: 따라서 양 쪽 엔티티 모두 수정해야 함
            Team team5 = new Team();
            team5.setName("team5");
            em.persist(team5);

            Member member5= new Member();
            member5.setUsername("name5");
            member5.setTeam(team5);
            em.persist(member5);

            //team5.getMembers().add(member4); // 양 쪽 다 저장
            member5.changeTeam(team5);

            em.flush();
            em.clear();

            /*프록시*/
            // 실제 엔티티의 메소드 사용 가능
            Member reference3 = em.getReference(Member.class, member3.getId());
            System.out.println("프록시 객체: "+reference3.getUsername()); // 실제 메소드 요청될 때 프록시 초기화 진행

            // 프록시 객체 =! 엔티티 클래스

           // 엔티티 조회 먼저
            Member m6 = em.find(Member.class, member5.getId());
            System.out.println("find 객체: "+m6.getClass());
            Member reference1 = em.getReference(Member.class, member5.getId());
            System.out.println("프록시 객체: "+reference1.getClass());

            // 프록시 객체 초기화 먼저
            Member reference2 = em.getReference(Member.class, member4.getId());
            System.out.println("프록시 객체: "+reference2.getClass());
            Member m7 = em.find(Member.class, member4.getId());
            System.out.println("find 객체: "+m7.getClass());

            /*프록시 메소드*/
            // 초기화 여부 확인
            Member reference4 = em.getReference(Member.class, member2.getId());
            System.out.println("초기화 여부 확인"+ emf.getPersistenceUnitUtil().isLoaded(reference4));

            //프록시 클래스 확인
            System.out.println("초기화 여부 확인"+ reference4.getClass());

            // 프록시 강제 초기화
            Hibernate.initialize(reference4);


            /*
            프록시 지연로딩
            */
            Team team8 = new Team();
            team8.setName("team8");

            Member member8 = new Member();
            member8.setUsername("name8");
            member8.setTeam(team8);

            em.persist(member8);
            em.flush();
            em.clear();

            Member m8 = em.find(Member.class, member8.getId());

            System.out.println(m8.getTeam().getClass());
            System.out.println("===========");
            m8.getTeam().getClass(); // 지연로딩 이째 프록시로 조회 함
            System.out.println("===========");


            /*
            영속성 전이 CASCADE , 고아객체
            * */
            Child child1 = new Child();
            Child child2 = new Child();

            Parent parent = new Parent();
            parent.addChild(child1);
            parent.addChild(child2);

            em.persist(parent); // CASCADE에 의해 Child까지 persist 됨
            em.flush();
            em.clear();

            Parent findParent1 = em.find(Parent.class, parent.getId());
            findParent1.getChildList().remove(0); // orphan객체 같이 삭제



            // 쓰기지연 sql 저장소에 모든 sql 실행
            tx.commit(); // 트랜섹션 요청 실행
        }catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close();// 엔티티매니저 종료
        }
        emf.close();
    }
}
