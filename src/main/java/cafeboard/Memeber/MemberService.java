package cafeboard.Memeber;

import cafeboard.SecurityUtils;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //회원가입 생성 ( + 비밀번호 해쉬화 하기)
    public void create(CreateMemberRequest request){
        memberRepository.save(new Member(
                request.username(),
                SecurityUtils.sha256EncryptHex1(request.password()),
                request.nickname()));
    }

    //회원 탈퇴
    public void delete(Long memberId){
        memberRepository.deleteById(memberId);
    }
}
