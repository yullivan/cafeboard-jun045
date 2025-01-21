package cafeboard.Board;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BoardController {

    private BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    //생성
    @PostMapping("/boards")
    public void create(@RequestBody CreateBoardRequest request){
        boardService.create(request);
    }

    //수정
    @PutMapping("/boards/{boardId}")
    public Board updateBoard(@PathVariable Long boardId, @RequestBody CreateBoardRequest request){
        Board updatedboard = boardService.update(boardId,request);
        return updatedboard;
    }

    //조회
    @GetMapping("/boards")
    public List<BoardResponse> findAll(){
        return boardService.findAll();
    }

    //특정 게시판의 게시글 목록 조회
    @GetMapping("/boards/{boardId}")
    public BoardDetailResponse findDetail(@PathVariable Long boardId){
        return boardService.findDetail(boardId);
    }


    //삭제
    @DeleteMapping("/boards/{boardId}")
    public void delete(@PathVariable Long boardId){
        boardService.delete(boardId);
    }
}
