package cafeboard;

import cafeboard.Post.Post;
import cafeboard.Post.PostMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MapperTest {

    @Autowired
    PostMapper postMapper;

    @Test
    void name() {
        postMapper.findAll(null);
        postMapper.findAll(3L);
    }
}
