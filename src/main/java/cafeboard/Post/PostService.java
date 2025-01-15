package cafeboard.Post;

import cafeboard.Board.Board;
import cafeboard.Board.BoardRepository;
import cafeboard.Comment.Comment;
import cafeboard.Comment.CommentRepository;
import cafeboard.Comment.CommentResponse;
import cafeboard.Memeber.Member;
import cafeboard.Memeber.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//게시글에 대한 회원정보 추가

@Service
public class PostService {

    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    public PostService(PostRepository postRepository, BoardRepository boardRepository, CommentRepository commentRepository, MemberRepository memberRepository) {
        this.postRepository = postRepository;
        this.boardRepository = boardRepository;
        this.commentRepository = commentRepository;
        this.memberRepository = memberRepository;
    }

    //생성(게시판 있으면 게시글 생성 가능) + 회원인지 확인 후 추가
    public void create(CreatePostRequest request){
        Board board = boardRepository.findById(request.boardId()).orElseThrow(() -> new IllegalArgumentException("게시판 찾을 수 없음"));
        Member member = memberRepository.findById(request.writerId()).orElseThrow(()-> new IllegalArgumentException("회원 정보가 올바르지 않음"));
        postRepository.save(new Post(request.title(),request.content(), board, member));
    }

    //목록조회(게시글id, 댓글 개수)
    public List<PostResponse> fingAll(){
        List <Post> posts = postRepository.findAll();
        return posts.stream()
                .map(post -> new PostResponse(
                        post.getId(),
                        post.getTitle(),
                        commentRepository.countByPostId(post.getId())  //특정 게시물에 대한 댓글 개수 1:n
                        // Post의 ID를 사용하여 해당 Post에 속하는 Comment의 개수를 계산
                        ))
                .toList();
    } //수동으로 댓글 개수를 관리 : commentCount 필드 유지 / 실시간으로 조회 : 엔티티에 있는 commentCount 삭제, 동적으로 조회하는 방식

    //상세조회(댓글 목록 포함)
    //각 댓글을 적절한 DTO로 변환하고 그 결과를 PostDetailResponse에 담아 반환
    public PostDetailResponse findDetail(Long id){
        Post post = postRepository.findById(id).orElseThrow();  //게시글 조회
        List<Comment> comments = commentRepository.findByPostId(id); //게시글에 해당하는 댓글목록 조회

        return new PostDetailResponse(
                post.getId(),
                post.getTitle(),
                post.getPostcontent(),
                comments.stream()
                        .map(comment -> new CommentResponse(
                                comment.getId(),
                                comment.getComment()))
                        .toList()
                //postdetailresponse에서 comments의 반환값이 list<commentresponse> 이므로
                // List<comment> 상태인 comments를 .map 사용해 변환해줘야함
        );
    }

    //수정
    // 추가) 작성한 회원인지 확인 후 게시글 수정
    @Transactional  //transactioinal 때문에 save안해도 됨
    public Post update(Long id, CreatePostRequest request){
        Post post = postRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 게시글"));

        //작성한 회원과 일치하는지 확인
//        if( post.getWriter().getId().equals())

        post.setTitle(request.title());
        post.setPostcontent(request.content());

        return post;
    }

    //삭제 (작성자인지 확인, 게시글 있는지 확인, 삭제)
    public void delete(Long id){
        Post post = postRepository.findById(id).orElseThrow( ()-> new IllegalArgumentException("존재하지 않는 게시물이므로 삭제 불가"));
        postRepository.deleteById(id);
    }
}
