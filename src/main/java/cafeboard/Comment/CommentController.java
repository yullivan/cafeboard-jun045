package cafeboard.Comment;

import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //생성
    @PostMapping("/comments")
    public void create(@RequestBody CreateCommentRequest request){
        commentService.create(request);
    }

    //수정
    @PutMapping("/comments/{commentId}")
    public Comment update(@PathVariable Long id, @RequestBody CreateCommentRequest request){
        Comment comment = commentService.update(id,request);
        return comment;
    }

    //삭제
    @DeleteMapping("/comments/{commentId}")
    public void delete(@PathVariable Long id, @RequestBody CreateCommentRequest request){
        commentService.delete(id);
    }
}
