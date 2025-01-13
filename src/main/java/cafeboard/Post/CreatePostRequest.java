package cafeboard.Post;

import cafeboard.Board.Board;

public record CreatePostRequest(String title,
                                String content,
                                Long boardId) {
}
