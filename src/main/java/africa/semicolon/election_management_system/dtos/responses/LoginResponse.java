package africa.semicolon.election_management_system.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    @JsonProperty("user_id")
    private Long id;
    @JsonProperty("unique_identifier")
    private String username;
    private String message;
    private String token;
}
