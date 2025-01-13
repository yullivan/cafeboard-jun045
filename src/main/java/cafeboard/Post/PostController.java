package cafeboard.Post;

import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //생성
    @PostMapping("/posts")
    public void create(@RequestBody CreatePostRequest request){
        postService.create(request);
    }

    //수정
    @PutMapping("/posts/{postId}")
    public Post update(@PathVariable Long id, @RequestBody CreatePostRequest request){
        Post post = postService.update(id,request);
        return post;
    }

    //목록조회

    //상세조회

    //삭제
    @DeleteMapping("/posts/{postId}")
    public void delete(@PathVariable Long id){
        postService.delete(id);
    }
}
