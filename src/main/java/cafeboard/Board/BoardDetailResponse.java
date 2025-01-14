package cafeboard.Board;

import cafeboard.Post.Post;
import cafeboard.Post.PostResponse;

import java.util.List;

public record BoardDetailResponse(Long id,
                                  String name,
                                  List<PostResponse> posts
                                  ) {
}
