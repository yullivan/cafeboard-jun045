package cafeboard.Comment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    //Post 객체의 id를 기준으로, Comment 엔티티에서 해당 Post에 속하는 모든 댓글을 찾아 그 개수를 계산
    int countByPostId(Long postid);

    List<Comment> findByPostId(Long id);
}
