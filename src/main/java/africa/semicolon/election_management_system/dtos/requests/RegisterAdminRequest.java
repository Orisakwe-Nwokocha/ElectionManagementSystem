package africa.semicolon.election_management_system.dtos.requests;


import africa.semicolon.election_management_system.data.constants.Role;
import lombok.Data;

@Data
public class RegisterAdminRequest {
    private String username;
    private String password;
}
