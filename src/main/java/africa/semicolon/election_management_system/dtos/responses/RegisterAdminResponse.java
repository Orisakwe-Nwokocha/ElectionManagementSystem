package africa.semicolon.election_management_system.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RegisterAdminResponse {
    private Long id;
    private String username;
    private String message;
}
