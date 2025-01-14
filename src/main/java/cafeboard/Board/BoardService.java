package cafeboard.Board;

import cafeboard.Comment.CommentRepository;
import cafeboard.Post.Post;
import cafeboard.Post.PostRepository;
import cafeboard.Post.PostResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public BoardService(BoardRepository boardRepository, PostRepository postRepository, CommentRepository commentRepository) {
        this.boardRepository = boardRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    //생성
    //클라이언트로부터 전달된 게시판 생성 요청을 처리하기 위해 CreateBoardRequest 객체를 파라미터로
    public void create(CreateBoardRequest request) {
//        boardRepository.save(new Board(request.name()));
        try {
            boardRepository.save(new Board(request.name()));
        } catch (Exception e) {
            throw new RuntimeException("게시판 생성 중 오류 발생", e); // 예외를 던져서 처리
        }
    }

    @Transactional
    //수정
    public Board update(Long boardid,CreateBoardRequest request){
        Board boardToUpdate = boardRepository.findById(boardid).orElseThrow();

        boardToUpdate.setBoardName(request.name());
        return boardRepository.save(boardToUpdate);
    }

    //조회(전체)
    //모든 게시판을 조회하는 메서드로, 조회에 필요한 추가적인 입력 데이터가 없기 때문에 파라미터가 없다
    public List<BoardResponse> findAll(){
        List<Board> boards = boardRepository.findAll();

        return boards.stream()
                .map(board -> new BoardResponse(board.getId(),
                        board.getBoardName()))
                .toList();
    }

    //삭제
    public void delete(Long id){
        boardRepository.deleteById(id);
    }

    //특정게시판의 게시글 목록 조회
    public BoardDetailResponse findDetail(Long id){
        //게시판 조회
        Board board = boardRepository.findById(id).orElseThrow();
        //게시글 목록 조회(1게시판 n게시글) - board id를 기준으로 게시글 목록 찾기
        List<Post> posts = postRepository.findByBoardId(id);

        //detailresponse로 반환
        return new BoardDetailResponse(
                board.getId(),
                board.getBoardName(),
                posts.stream()
                        .map(post -> new PostResponse(
                                post.getId(),
                                post.getTitle(),
                                commentRepository.countByPostId(post.getId())
                                )).toList()

                //postResponse에 들어있는 카운트는 int인데 string으로 들어감
                //3번째 commentcount값 불러오러면 commentRepository에서 불러와야함
                //왜? commentcount는 댓글개수 받기위해 만들어둠 -> 세려면 commentRepository 안에서 함수 생성해야함
        );
    }
}
