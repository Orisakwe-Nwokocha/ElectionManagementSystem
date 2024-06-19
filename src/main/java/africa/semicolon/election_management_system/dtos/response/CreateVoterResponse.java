package africa.semicolon.election_management_system.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateVoterResponse {
    private String identificationNumber;
    private String message;
    private String dateRegistered;
    private Long id;

}
