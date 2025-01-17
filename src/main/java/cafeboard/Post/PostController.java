package cafeboard.Post;

import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Post update(@PathVariable Long postId, @RequestBody CreatePostRequest request){
        Post post = postService.update(postId,request);
        return post;
    }

    //목록조회
    @GetMapping("/posts")
    public List<PostResponse> findAllposts(){
        return postService.findAll();
    }

    //상세조회
    @GetMapping("/posts/{postId}")
    public PostDetailResponse findDetailpost(@PathVariable Long postId){
        return postService.findDetail(postId);
    }


    //삭제
    @DeleteMapping("/posts/{postId}")
    public void delete(@PathVariable Long postId){
        postService.delete(postId);
    }
}
