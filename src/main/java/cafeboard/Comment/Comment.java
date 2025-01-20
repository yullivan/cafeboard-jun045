package cafeboard.Comment;

import cafeboard.Post.Post;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private String userid;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne
//    @JoinColumn(name = "post_id") 필수 아님
    private Post post;

    protected Comment() {
    }

    public Comment(String comment, Post post, String userid) {
        this.comment = comment;
        this.post = post;
        this.userid = userid;
    }

    public Long getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public String getUserid() {
        return userid;
    }

    public Comment(String userid) {
        this.userid = userid;
    }

    public Post getPost() {
        return post;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
