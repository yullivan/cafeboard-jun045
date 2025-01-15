package cafeboard.Comment;

import cafeboard.Post.Post;
import cafeboard.Post.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    //생성(게시글 있으면 댓글 생성)
    public void create(CreateCommentRequest request){
        Post post = postRepository.findById(request.postId()).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        commentRepository.save(new Comment(request.comment(),post));
    }

    //수정
    @Transactional
    public Comment update(Long id, CreateCommentRequest request){
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));
        comment.setComment(request.comment());

        commentRepository.save(comment);
        return comment;
    }

    //삭제
    public void delete(Long id){
        commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("삭제할 댓글이 존재하지 않습니다."));
        commentRepository.deleteById(id);
    }
}
