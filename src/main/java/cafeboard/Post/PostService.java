package cafeboard.Post;

import cafeboard.Board.Board;
import cafeboard.Board.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final BoardRepository boardRepository;

    public PostService(PostRepository postRepository, BoardRepository boardRepository) {
        this.postRepository = postRepository;
        this.boardRepository = boardRepository;
    }

    //생성(게시판 있으면 생성 가능)
    public void create(CreatePostRequest request){
        Board board = boardRepository.findById(request.boardId()).orElseThrow();
        postRepository.save(new Post(request.title(),request.content(), board));
    }

    //목록조회(게시글id, 댓글 개수)
    public List<PostResponse> fingAll(){
        List <Post> posts = postRepository.findAll();
        return posts.stream()
                .map(post -> new PostResponse(
                        post.getId(),
                        post.getTitle(),
                        post.getPostcontent()
                        ))
                .toList();
    }

    //상세조회(댓글 목록 포함)
    public PostResponse findDetail(Long id){

    }

    //수정
    @Transactional
    public Post update(Long id, CreatePostRequest request){
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(request.title());
        post.setPostcontent(request.content());

        postRepository.save(post);
        return post;
    }

    //삭제(
    public void delete(Long id){
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.deleteById(id);
    }
}
