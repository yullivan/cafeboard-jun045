package cafeboard;

import cafeboard.Board.Board;
import cafeboard.Board.BoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Test
    void 저장시_현재시각으로_필드_채워지는지_확인() {
        // Given: 새 Board 객체 생성
        Board board = new Board("게시판1");

        // 초기 상태 확인 (ID, CreatedAt, UpdatedAt이 모두 null이어야 함)
        assertThat(board.getId()).isNull();
        assertThat(board.getCreatedAt()).isNull();
        assertThat(board.getUpdatedAt()).isNull();

        // When: Board 저장
        boardRepository.save(board);

        // Then: Board 저장 후 필드들이 채워졌는지 확인
        assertThat(board.getId()).isNotNull(); // ID가 자동으로 생성되어야 함
        assertThat(board.getCreatedAt()).isNotNull(); // CreatedAt이 현재 시각으로 설정되어야 함
        assertThat(board.getUpdatedAt()).isNotNull(); // UpdatedAt이 현재 시각으로 설정되어야 함
    }
}
