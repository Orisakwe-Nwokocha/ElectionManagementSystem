package africa.semicolon.election_management_system.dtos.requests;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class CreateVoterRequest {
    private String name;
    private String password;
    private String address;
    private LocalDate dateOfBirth;
    private String stateOfOrigin;
}
