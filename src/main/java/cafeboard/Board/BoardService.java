package cafeboard.Board;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    //생성
    //클라이언트로부터 전달된 게시판 생성 요청을 처리하기 위해 CreateBoardRequest 객체를 파라미터로
    public void create(CreateBoardRequest request) {
        boardRepository.save(new Board(request.name()));
    }

    //수정
    public Board update(Long boardid,CreateBoardRequest request){
        Board 수정할board = boardRepository.findById(boardid).orElseThrow();

        수정할board.setBoardName(request.name());
        boardRepository.save(수정할board);

        return 수정할board;
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
}
