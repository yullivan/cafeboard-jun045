package cafeboard.Comment;

import cafeboard.JwtProvider;
import cafeboard.Memeber.LoginResponse;
import cafeboard.Memeber.Member;
import cafeboard.Memeber.MemberRepository;
import cafeboard.Post.Post;
import cafeboard.Post.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository, MemberRepository memberRepository, JwtProvider jwtProvider) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
        this.jwtProvider = jwtProvider;
    }

    //생성(게시글 있으면 댓글 생성)
    public void create(CreateCommentRequest request, String username){
        Post post = postRepository.findById(request.postId()).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        Member member = memberRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("회원정보 찾을 수 없음"));

        commentRepository.save(new Comment(request.comment(),request.userId(), member, post));
    }

    //수정
    @Transactional
    public Comment update(Long id, CreateCommentRequest request){
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));
        comment.setComment(request.comment());

        return comment;
    }

    //삭제
    public void delete(Long id, String username){
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("삭제할 댓글이 존재하지 않습니다."));
        Member member = memberRepository.findById(id).orElseThrow();

        if(!comment..equals(username)){
            throw new IllegalArgumentException("회원정보 일치하지 않음");
        }

        commentRepository.deleteById(id);
    }
}
