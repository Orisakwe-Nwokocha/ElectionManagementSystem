package africa.semicolon.election_management_system.dtos.requests;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class UpdateVoterRequest {
    private Long id;
    private String name;
    private String password;
    private String address;
    private LocalDate dateOfBirth;
    private String stateOfOrigin;
    private String identificationNumber;
}
