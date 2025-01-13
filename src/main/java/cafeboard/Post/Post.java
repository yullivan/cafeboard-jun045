package cafeboard.Post;

import cafeboard.Board.Board;
import cafeboard.Comment.Comment;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String postcontent;

    @ManyToOne
    private Board board;
    //생성자 같이 만들어줘야함

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments;


    protected Post() {
    }

    public Post(String title, String postcontent,Board board) {
        this.title = title;
        this.postcontent = postcontent;
        this.board = board;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPostcontent(String postcontent) {
        this.postcontent = postcontent;
    }
}
