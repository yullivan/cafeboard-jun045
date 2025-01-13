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
    public void create(CreateBoardRequest request){
        boardService.create(request);
    }

    //수정
    @PutMapping("/boards/{boardId}")
    public Board updateBoard(@PathVariable Long id, @RequestBody CreateBoardRequest request){
        Board updatedboard = boardService.update(id,request);
        return updatedboard;
    }

    //조회
    @GetMapping("/boards")
    public List<BoardResponse> findAll(){
        return boardService.findAll();
    }

    //삭제
    @DeleteMapping("/boards/{boardId}")
    public void delete(@PathVariable Long id){
        boardService.delete(id);
    }
}
