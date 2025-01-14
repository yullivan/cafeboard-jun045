package cafeboard.Post;

public record PostResponse(Long id,
                           String title,
                           int commentCount
                           //댓글 개수를 API 응답으로 클라이언트에 전달하려면,
                           // PostResponse 객체에 댓글 개수를 담아야함
                           //postService 목록조회(댓글 개수)에서 사용중
                          ) {
}
