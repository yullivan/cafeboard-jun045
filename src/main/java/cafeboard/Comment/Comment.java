package cafeboard.Comment;

import cafeboard.Post.Post;
import jakarta.persistence.*;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;

    @ManyToOne
    private Post post;

    protected Comment() {
    }

    public Comment(String comment, Post post) {
        this.comment = comment;
        this.post = post;
    }

    public String getComment() {
        return comment;
    }

    public Post getPost() {
        return post;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
