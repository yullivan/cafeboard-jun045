package cafeboard.Post;

import cafeboard.Board.Board;
import cafeboard.Comment.Comment;
import cafeboard.Memeber.Member;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String postcontent;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Board board;
    //생성자 같이 만들어줘야함

    @ManyToOne
    @JoinColumn(nullable = false)
    private Member writer;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments;


    protected Post() {
    }

    public Post(String title, String postcontent,Board board, Member writer) {
        this.title = title;
        this.postcontent = postcontent;
        this.board = board;
        this.writer = writer;
    }


    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPostcontent() {
        return postcontent;
    }

    public Member getWriter() {
        return writer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPostcontent(String postcontent) {
        this.postcontent = postcontent;
    }
}
