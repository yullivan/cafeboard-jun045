package cafeboard.Memeber;

import cafeboard.SecurityUtils;
import jakarta.persistence.*;

//멤버:게시글 = 1:n
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;  // login id

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    protected Member() {
    }

    public Member(String username, String password, String nickname) {
        this.username = username;
        this.password = password;

        //닉네임 비어있으면 그자리에 유저네임 넣기
        this.nickname = nickname == null ? username : nickname;
    }

    //getter
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    //찾은 회원의 비밀번호와 클라이언트 전달한 비밀번호 비교 메소드 만듦
    public boolean equalpassword(String password){
        return this.password.equals(SecurityUtils.sha256EncryptHex1(password));
    }

}
