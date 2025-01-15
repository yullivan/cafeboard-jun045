package cafeboard;

import cafeboard.Board.CreateBoardRequest;
import cafeboard.Comment.CreateCommentRequest;
import cafeboard.Memeber.CreateMemberRequest;
import cafeboard.Post.CreatePostRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 게시판생성() { //createrequest는 json형태 -> requestbody
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateBoardRequest("게시판1"))  // request body에 CreateBoardRequest를 JSON 형태로 변환
                .when()
                .post("/boards")  // POST 요청
                .then().log().all()
                .statusCode(200);  // 상태 코드가 200이어야 성공
    }

    @Test
    void 게시판수정() { //path + body
        // 1. 게시판을 생성하고, 생성된 게시판의 ID를 받아옵니다.
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateBoardRequest("게시판1"))  // request body에 CreateBoardRequest를 JSON 형태로 변환
                .when()
                .post("/boards")  // POST 요청
                .then().log().all()
                .statusCode(200);  // 상태 코드가 200이어야 성공

        // 2. 생성된 게시판의 ID를 사용하여, 게시판 이름을 수정하는 요청을 보냅니다.
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateBoardRequest("게시판이름수정1"))
                .pathParam("boardId", 1)  // 임시로 boardId 1을 사용
                .when()
                .put("/boards/{boardId}")  // PUT 요청으로 게시판 수정
                .then().log().all()
                .statusCode(200);  // 상태 코드 200으로 성공 시 실행
    }

    @Test
    void 게시판전체조회() {
        RestAssured
                .given()
                .when()
                .get("/boards") // 서버로 GET /products 요청
                .then()
                .statusCode(200); // 요청에 대한 서버 응답의 상태코드가 200인지 검증
    }

    @Test
    void 게시판상세조회() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateBoardRequest("게시판1"))
                .when()
                .post("/boards") // POST /members 요청
                .then().log().all()
                .statusCode(200);

        RestAssured
                .given().log().all()
                .pathParam("boardId", "1")
                .when()
                .get("/boards/{boardId}") // 서버로 GET /products 요청
                .then().log().all()
                .statusCode(200); // 요청에 대한 서버 응답의 상태코드가 200인지 검증
    }

    @Test
    void 게시판삭제() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateBoardRequest("게시판1"))
                .when()
                .post("/boards") // POST /members 요청
                .then().log().all()
                .statusCode(200);

        RestAssured
                .given().log().all()
                .pathParam("boardId", "1")
                .when()
                .delete("/boards/{boardId}") // 서버로 GET /products 요청
                .then().log().all()
                .statusCode(200); // 요청에 대한 서버 응답의 상태코드가 200인지 검증
    }

    @Test
    void 게시글생성() { //createrequest는 json형태 -> requestbody
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateBoardRequest("게시판1"))
                .when()
                .post("/boards") // POST /members 요청
                .then().log().all()
                .statusCode(200);

        Long boardId = 1L;  // 테스트 환경에서 사용될 boardId 값 (이미 생성된 게시판 ID)

        // 회원 생성 (writerId = 1L로 미리 등록된 회원)
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateMemberRequest("loginid1234", "password", "testNick"))
                .when()
                .post("/members")
                .then().log().all()
                .statusCode(200);

        //회원 ID 설정 (테스트 환경에서 사용될 writerId 값)
        Long writerId = 1L; // 실제로는 테스트 중인 회원 ID를 설정해줘야 함

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreatePostRequest("게시글1","가나라다", boardId, writerId))  // request body에 CreateBoardRequest를 JSON 형태로 변환
                .when()
                .post("/posts")  // POST 요청
                .then().log().all()
                .statusCode(200);  // 상태 코드가 200이어야 성공
    }

    @Test
    void 게시글수정() { //path + body
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateBoardRequest("게시판1"))
                .when()
                .post("/boards") // POST /members 요청
                .then().log().all()
                .statusCode(200);

        Long boardId = 1L;  // 테스트 환경에서 사용될 boardId 값 (이미 생성된 게시판 ID)

        // 회원 생성 (writerId = 1L로 미리 등록된 회원)
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateMemberRequest("loginid1234", "password", "testNick"))
                .when()
                .post("/members")
                .then().log().all()
                .statusCode(200);

        //회원 ID 설정 (테스트 환경에서 사용될 writerId 값)
        Long writerId = 1L; // 실제로는 테스트 중인 회원 ID를 설정해줘야 함

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreatePostRequest("게시글1","가나라다", boardId, writerId))  // request body에 CreateBoardRequest를 JSON 형태로 변환
                .when()
                .post("/posts")  // POST 요청
                .then().log().all()
                .statusCode(200);  // 상태 코드가 200이어야 성공

        // 2. 생성된 게시판의 ID를 사용하여, 게시판 이름을 수정하는 요청을 보냅니다.
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreatePostRequest("게시글이름수정1", "글 내용 수정",boardId, writerId))
                .pathParam("postId", 1)  // 임시로 boardId 1을 사용
                .when()
                .put("/posts/{postId}")  // PUT 요청으로 게시판 수정
                .then().log().all()
                .statusCode(200);  // 상태 코드 200으로 성공 시 실행
    }

    @Test
    void 게시글전체조회() { //path + body
        RestAssured
                .given()
                .when()
                .get("/posts") // 서버로 GET /products 요청
                .then()
                .statusCode(200); // 요청에 대한 서버 응답의 상태코드가 200인지 검증

    }

    @Test
    void 게시글상세조회() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateBoardRequest("게시판1"))
                .when()
                .post("/boards") // POST /members 요청
                .then().log().all()
                .statusCode(200);

        Long boardId = 1L;  // 테스트 환경에서 사용될 boardId 값 (이미 생성된 게시판 ID)

        // 회원 생성 (writerId = 1L로 미리 등록된 회원)
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateMemberRequest("loginid1234", "password", "testNick"))
                .when()
                .post("/members")
                .then().log().all()
                .statusCode(200);

        //회원 ID 설정 (테스트 환경에서 사용될 writerId 값)
        Long writerId = 1L; // 실제로는 테스트 중인 회원 ID를 설정해줘야 함

        //게시글 생성
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreatePostRequest("게시글1","가나라다", boardId, writerId))  // request body에 CreateBoardRequest를 JSON 형태로 변환
                .when()
                .post("/posts")  // POST 요청
                .then().log().all()
                .statusCode(200);  // 상태 코드가 200이어야 성공

        //조회
        RestAssured
                .given().log().all()
                .pathParam("postId", 1)
                .when()
                .get("/posts/{postId}") // 서버로 GET /products 요청
                .then().log().all()
                .statusCode(200); // 요청에 대한 서버 응답의 상태코드가 200인지 검증
    }

    @Test
    void 게시글삭제() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateBoardRequest("게시판1"))
                .when()
                .post("/boards") // POST /members 요청
                .then().log().all()
                .statusCode(200);

        Long boardId = 1L;  // 테스트 환경에서 사용될 boardId 값 (이미 생성된 게시판 ID)

        // 회원 생성 (writerId = 1L로 미리 등록된 회원)
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateMemberRequest("loginid1234", "password", "testNick"))
                .when()
                .post("/members")
                .then().log().all()
                .statusCode(200);

        //회원 ID 설정 (테스트 환경에서 사용될 writerId 값)
        Long writerId = 1L; // 실제로는 테스트 중인 회원 ID를 설정해줘야 함

        //생성
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreatePostRequest("게시글1","가나라다", boardId, writerId))  // request body에 CreateBoardRequest를 JSON 형태로 변환
                .when()
                .post("/posts")  // POST 요청
                .then().log().all()
                .statusCode(200);  // 상태 코드가 200이어야 성공

        //삭제
        RestAssured
                .given().log().all()
                .pathParam("postId", 1)
                .when()
                .delete("/posts/{postId}") // 서버로 GET /products 요청
                .then().log().all()
                .statusCode(200); // 요청에 대한 서버 응답의 상태코드가 200인지 검증
    }

    @Test
    void 댓글생성() { //createrequest는 json형태 -> requestbody
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateBoardRequest("게시판1"))
                .when()
                .post("/boards") // POST /members 요청
                .then().log().all()
                .statusCode(200);

        Long boardId = 1L;  // 테스트 환경에서 사용될 boardId 값 (이미 생성된 게시판 ID)

        // 회원 생성 (writerId = 1L로 미리 등록된 회원)
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateMemberRequest("loginid1234", "password", "testNick"))
                .when()
                .post("/members")
                .then().log().all()
                .statusCode(200);

        //회원 ID 설정 (테스트 환경에서 사용될 writerId 값)
        Long writerId = 1L; // 실제로는 테스트 중인 회원 ID를 설정해줘야 함

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreatePostRequest("게시글1","가나라다", boardId, writerId))  // request body에 CreateBoardRequest를 JSON 형태로 변환
                .when()
                .post("/posts")  // POST 요청
                .then().log().all()
                .statusCode(200);  // 상태 코드가 200이어야 성공

        Long postId = 1L;

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateCommentRequest("댓글생성1", postId))  // request body에 CreateBoardRequest를 JSON 형태로 변환
                .when()
                .post("/comments")  // POST 요청
                .then().log().all()
                .statusCode(200);  // 상태 코드가 200이어야 성공
    }

    @Test
    void 댓글수정() { //path + body
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateBoardRequest("게시판1"))
                .when()
                .post("/boards") // POST /members 요청
                .then().log().all()
                .statusCode(200);

        Long boardId = 1L;  // 테스트 환경에서 사용될 boardId 값 (이미 생성된 게시판 ID)

        // 회원 생성 (writerId = 1L로 미리 등록된 회원)
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateMemberRequest("loginid1234", "password", "testNick"))
                .when()
                .post("/members")
                .then().log().all()
                .statusCode(200);

        //회원 ID 설정 (테스트 환경에서 사용될 writerId 값)
        Long writerId = 1L; // 실제로는 테스트 중인 회원 ID를 설정해줘야 함

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreatePostRequest("게시글1","가나라다", boardId, writerId))  // request body에 CreateBoardRequest를 JSON 형태로 변환
                .when()
                .post("/posts")  // POST 요청
                .then().log().all()
                .statusCode(200);  // 상태 코드가 200이어야 성공

        Long postId = 1L;

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateCommentRequest("댓글생성1", postId))  // request body에 CreateBoardRequest를 JSON 형태로 변환
                .when()
                .post("/comments")  // POST 요청
                .then().log().all()
                .statusCode(200);  // 상태 코드가 200이어야 성공

        // 댓글 수정
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateCommentRequest("댓글수정1", postId))  // 수정된 댓글 내용
                .pathParam("commentId", 1)  // 수정할 댓글의 ID를 pathParam으로 전달
                .when()
                .put("/comments/{commentId}")  // PUT 요청으로 댓글 수정
                .then().log().all()
                .statusCode(200);  // 상태 코드가 200이면 성공
    }


    @Test
    void 댓글삭제() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateBoardRequest("게시판1"))
                .when()
                .post("/boards") // POST /members 요청
                .then().log().all()
                .statusCode(200);

        Long boardId = 1L;  // 테스트 환경에서 사용될 boardId 값 (이미 생성된 게시판 ID)

        // 회원 생성 (writerId = 1L로 미리 등록된 회원)
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateMemberRequest("loginid1234", "password", "testNick"))
                .when()
                .post("/members")
                .then().log().all()
                .statusCode(200);

        //회원 ID 설정 (테스트 환경에서 사용될 writerId 값)
        Long writerId = 1L; // 실제로는 테스트 중인 회원 ID를 설정해줘야 함

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreatePostRequest("게시글1","가나라다", boardId, writerId))  // request body에 CreateBoardRequest를 JSON 형태로 변환
                .when()
                .post("/posts")  // POST 요청
                .then().log().all()
                .statusCode(200);  // 상태 코드가 200이어야 성공

        Long postId = 1L;

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new CreateCommentRequest("댓글생성1", postId))  // request body에 CreateBoardRequest를 JSON 형태로 변환
                .when()
                .post("/comments")  // POST 요청
                .then().log().all()
                .statusCode(200);  // 상태 코드가 200이어야 성공

        //삭제
        RestAssured
                .given().log().all()
                .pathParam("commentId", 1)
                .when()
                .delete("/comments/{commentId}")
                .then().log().all()
                .statusCode(200); // 요청에 대한 서버 응답의 상태코드가 200인지 검증
    }








}