package cafeboard.Post;

import cafeboard.Comment.Comment;

import java.util.List;

public record PostDetailResponse(Long id,
                                 String title,
                                 String content,
                                 List<Comment> comments) {
}
