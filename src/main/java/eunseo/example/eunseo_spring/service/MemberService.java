package eunseo.example.eunseo_spring.service;

import eunseo.example.eunseo_spring.domain.Member;
import eunseo.example.eunseo_spring.repository.MemberRepository;
import eunseo.example.eunseo_spring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {

    //private final MemberRepository memberRepository = new MemoryMemberRepository();


    private final MemberRepository memberRepository;
    //해결책! MemberService 객체 생성 시 저장소 객체를 넣어준다.
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }


    //회원 가입
    public Long join(Member member){
        validateDuplicateMember(member); //같은 이름 회원 안됨
        memberRepository.save(member);
        return member.getId();
    }
    private void validateDuplicateMember(Member member){
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    //전체 회원 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    //회원 조회
    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
