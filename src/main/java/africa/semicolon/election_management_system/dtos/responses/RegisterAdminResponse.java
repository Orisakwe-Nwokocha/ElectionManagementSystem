package africa.semicolon.election_management_system.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterAdminResponse {
    private Long id;
    private String username;
    private String message;
}
