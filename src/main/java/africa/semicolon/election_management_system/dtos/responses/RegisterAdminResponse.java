package africa.semicolon.election_management_system.dtos.responses;

import lombok.Data;

@Data
public class RegisterAdminResponse {
    private Long id;
    private String username;
    private String message;
}
