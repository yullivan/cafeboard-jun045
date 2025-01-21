package cafeboard.Memeber;

import jakarta.validation.constraints.NotBlank;

public record CreateMemberRequest(
        @NotBlank String username,
        @NotBlank String password,
        String nickname
) {
}
