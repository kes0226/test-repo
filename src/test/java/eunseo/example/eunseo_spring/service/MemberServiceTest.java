package eunseo.example.eunseo_spring.service;

import eunseo.example.eunseo_spring.domain.Member;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

import eunseo.example.eunseo_spring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class MemberServiceTest {

    //MemberService memberService = new MemberService();
    //MemoryMemberRepository memberRepository = new MemoryMemberRepository();

    // 테스트시, MemberService객체, MemoryRepository객체 두 개를 생성함
    //그런데 MemberService에서도 저장소 객체를 생성하니, MemberService에서 쓰는 저장소 객체와
    //MemberServiceTest에서의 저장소 객체는 다름.
    //테스트시 각 함수에서 memberService의 저장소 객체로 DB를 쓰고, afterEach에서는 새로 생성한 저장소 객체로 데이터를 초기화함.
    // 지금은 저장소 map이 static 이라서 클래스 단위라 괜찮지만, 나중에 문제 생길수도...

    //이렇게 new로 매번 저장소 객체를 새로 생성하면(MemberService, MemberServiceTest 각각 생성됨),
    //MemberService와 test코드는 다른 저장소 객체를 가지므로, 같은 내용이 아닐수도 있다....
    //일단 지금은 저장소 map이 static 이라서 클래스 단위라 괜찮지만, 나중에 문제 생길수도...

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach(){
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    } //이렇게 memberService 생성시 Test클래스에서 생성한 저장소 객체를 넣어주면
    // 테스트할 memberService는 항상 test클래스와 같은 저장소 객체로 test한다.


    @AfterEach
    public void afterEach(){
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        // given //주어진 데이터에 대해서
        Member member = new Member();
        member.setName("spring");

        // when // 이런 상황일 때
        Long saveId = memberService.join(member);

        // then // 이런 결과가 나온다
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());

    }
    @Test //예외에 대한 테스트
    public void 중복_회원_예외(){
        //given
        Member member1 = new Member();
        member1.setName("spring");
        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        //member2를 넣어 이름 중복일 때, IllgealStateException 에러가 뜨는지 확인
        /*try{
            memberService.join(member2);
            fail(); //오류 발생, catch문에 걸려야 하는데 실패함
        } catch(IllegalStateException e){
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
            //오류 메세지가 제대로 뜨는지 확인, 설정한 메세지와 같아야함
        }*/
        //try catch문을 편리하게 바꿀 수 있다.
        //assertThrows로 오류클래스 확인
        //assertThat로 오류 메세지 같은지 확인
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");


        //then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}