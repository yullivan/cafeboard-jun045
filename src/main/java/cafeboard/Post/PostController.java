package cafeboard.Post;

import cafeboard.JwtProvider;
import cafeboard.Memeber.LoginResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {

    private final PostService postService;
    private final JwtProvider jwtProvider;

    public PostController(PostService postService, JwtProvider jwtProvider) {
        this.postService = postService;
        this.jwtProvider = jwtProvider;
    }

    //생성
    @PostMapping("/posts")
    public void create(@RequestBody CreatePostRequest request,
                       @RequestHeader(HttpHeaders.AUTHORIZATION) String bearerToken){
        String token = null;
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            token = bearerToken.substring(7);
        }

        if (token == null) {
            throw new IllegalArgumentException("로그인 정보가 유효하지 않습니다");
        }

        // 유효한 JWT 토큰인지 검증
        if (jwtProvider.isValidToken(token) == false) {
            throw new IllegalArgumentException("로그인 정보가 유효하지 않습니다");
        }

        String username = jwtProvider.getSubject(token);

        postService.create(request, username);
    }

    //수정
    @PutMapping("/posts/{postId}")
    public Post update(@PathVariable Long postId, @RequestBody CreatePostRequest request){
        Post post = postService.update(postId,request);
        return post;
    }

    //목록조회
    @GetMapping("/posts")
    public List<PostResponse> findAllposts(){
        return postService.findAll();
    }

    //상세조회
    @GetMapping("/posts/{postId}")
    public PostDetailResponse findDetailpost(@PathVariable Long postId){
        return postService.findDetail(postId);
    }


    //삭제
    @DeleteMapping("/posts/{postId}")
    public void delete(@PathVariable Long postId,
                       @RequestHeader(HttpHeaders.AUTHORIZATION) String bearerToken){
        String token = null;
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            token = bearerToken.substring(7);
        }

        if (token == null) {
            throw new IllegalArgumentException("로그인 정보가 유효하지 않습니다");
        }

        // 유효한 JWT 토큰인지 검증
        if (jwtProvider.isValidToken(token) == false) {
            throw new IllegalArgumentException("로그인 정보가 유효하지 않습니다");
        }

        // 토큰 데이터 읽기 - 요청 보낸 사람이 누구인지 확인
        // 이 코드는 이메일로 로그인한다고 가정했음. 이메일이 아니어도 됨.
        String username = jwtProvider.getSubject(token);

        postService.delete(postId, username);
    }
}
