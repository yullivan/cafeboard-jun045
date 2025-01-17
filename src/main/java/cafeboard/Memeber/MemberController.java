package cafeboard.Memeber;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
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

    //로그인
    @PostMapping("/login")
    public LoginResponse user(@RequestBody LoginRequest request){
        LoginResponse member = memberService.login(request);

        return null;
    }
}
