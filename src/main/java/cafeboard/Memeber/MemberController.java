package cafeboard.Memeber;

import cafeboard.JwtProvider;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
public class MemberController {

    private final MemberService memberService;
    private final JwtProvider jwtProvider;

    public MemberController(MemberService memberService, JwtProvider jwtProvider) {
        this.memberService = memberService;
        this.jwtProvider = jwtProvider;
    }

    //회원가입
    @PostMapping("/members")
    public void create(@Valid @RequestBody CreateMemberRequest request){
        memberService.create(request);
    }

    //회원탈퇴
    @DeleteMapping("/members/{memberId}")
    public void delete(@PathVariable Long memberId){
        memberService.delete(memberId);
    }

    //로그인 처리
    @PostMapping("/login")
    public LoginResponse user(@RequestBody LoginRequest request){
        LoginResponse member = memberService.login(request);

        return null;
    }

    //로그인 유지(검증)
    @GetMapping("/me")
    public String getProfile(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        //클라이언트가 보낸 HTTP 요청 헤더에서 Authorization 값을 받아옴
        //Authorization 헤더는 일반적으로 토큰 기반 인증을 사용할 때 사용. 값은 주로 "Bearer [토큰]" 형식
        // HTTP 요청에서 특정 헤더 값을 메서드의 파라미터로 전달받을 때 사용

        String[] tokenFormat = authorization.split(" ");
        String tokenType = tokenFormat[0];
        String token = tokenFormat[1];

        // Bearer 토큰인지 검증
        if (tokenType.equals("Bearer") == false) {
            throw new IllegalArgumentException("로그인 정보가 유효하지 않습니다");
        }

        // 유효한 JWT 토큰인지 검증
        if (jwtProvider.isValidToken(token) == false) {
            throw new IllegalArgumentException("로그인 정보가 유효하지 않습니다");
        }

        // JWT 토큰에서 email을 끄집어냄
        String username = jwtProvider.getSubject(token);

        return username;
    }
}
