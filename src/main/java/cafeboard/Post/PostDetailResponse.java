package cafeboard.Post;

import cafeboard.Comment.Comment;
import cafeboard.Comment.CommentResponse;

import java.util.List;

public record PostDetailResponse(Long id,
                                 String title,
                                 String content,
                                 List<CommentResponse> comments
//Post 엔티티에 있는 List<Comment>를 바로 쓰지 않고,
// CommentResponse라는 별도의 DTO를 만들어서 댓글 데이터를 변환하여 사용하는 것이 좋음
) {
}
