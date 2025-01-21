package cafeboard.Comment;

import cafeboard.JwtProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {

    private final CommentService commentService;
    private final JwtProvider jwtProvider;

    public CommentController(CommentService commentService, JwtProvider jwtProvider) {
        this.commentService = commentService;
        this.jwtProvider = jwtProvider;
    }

    //생성
    @PostMapping("/comments")
    public void create(@RequestBody CreateCommentRequest request, @RequestHeader(HttpHeaders.AUTHORIZATION) String bearerToken){
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

        commentService.create(request,username);
    }

    //수정
    @PutMapping("/comments/{commentId}")
    public Comment update(@PathVariable Long commentId, @RequestBody CreateCommentRequest request){
        Comment comment = commentService.update(commentId,request);
        return comment;
    }

    //삭제
    @DeleteMapping("/comments/{commentId}")
    public void delete(@PathVariable Long commentId, @RequestHeader(HttpHeaders.AUTHORIZATION) String bearerToken){
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

        commentService.delete(commentId,username);
    }
}
