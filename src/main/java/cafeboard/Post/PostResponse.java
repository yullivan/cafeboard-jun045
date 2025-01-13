package cafeboard.Post;

public record PostResponse(Long id,
                           String title,
                           int commentCount
                          ) {
}
