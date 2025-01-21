package cafeboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Test {

    @Autowired
    JwtProvider jwtProvider;

    @org.junit.jupiter.api.Test
    void name() {
        String token = jwtProvider.createToken("a@gmail.com");
        System.out.println("token = " +token);

        Boolean validationResult = jwtProvider.isValidToken("a@gmail.com");
        System.out.println("validationResult = " + validationResult);

        String email = jwtProvider.getSubject(token);
        System.out.println("email = " +email);

        String username = jwtProvider.getSubject(token);
        System.out.println("username = " +username);
    }
}
