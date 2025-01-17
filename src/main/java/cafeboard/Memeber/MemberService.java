package cafeboard.Memeber;

import cafeboard.JwtProvider;
import cafeboard.SecurityUtils;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    public MemberService(MemberRepository memberRepository, JwtProvider jwtProvider) {
        this.memberRepository = memberRepository;
        this.jwtProvider = jwtProvider;
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

    //로그인(id, 비밀번호 같은지 확인) username 찾고 pass 확인
    // 리포지토리에서 아이디찾고 반환, 있으면 리포지토리에 저장된 회원 비밀번호(getPassword) 불러와서 비교
    //username은 스트링값이므로 리포지토리에 findbyusername 함수 만듦
    public LoginResponse login(LoginRequest request){
        Member member = memberRepository.findByUsername(request.username())
                .orElseThrow(() -> new IllegalArgumentException("ID 또는 PASSWORD 일치하지 않음"));

        //회원이 입력한 비밀번호도 해쉬화해서 비교(아닐 경우 비교시 불일치만 나옴)
        //string값이니까 equals로 비교
       if (!member.getPassword().equals(SecurityUtils.sha256EncryptHex1(request.password()))){
           throw new IllegalArgumentException("ID 또는 PASSWORD 일치하지 않음");
       }

        //리턴 시 member가 아니라 로그인 인증서 줌 --> JWT 토큰
        //토큰 담을 리스폰스 레코드(dto) 새로 만들어서 주는게 좋음 (jwt에서 유저 아이디로 토큰 생성 후 로그인 리스폰스로 반환)
        return new LoginResponse(jwtProvider.createToken(request.username()));
    }


}
