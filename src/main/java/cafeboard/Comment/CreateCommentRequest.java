package cafeboard.Comment;

public record CreateCommentRequest(String comment,
                                   Long userId,
                                   Long memberId,
                                   Long postId){
//회원 - 게시글 아이디 - 댓글 아이디 - 댓글
}
