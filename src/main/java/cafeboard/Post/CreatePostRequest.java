package cafeboard.Post;

import cafeboard.Board.Board;

public record CreatePostRequest(String title,
                                String content,
                                Long boardId
                                //Long writerId //로그인 기능 구현되면 필요없는 데이터
) {
}
