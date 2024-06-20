package africa.semicolon.election_management_system.dtos.requests;

import africa.semicolon.election_management_system.data.constants.Category;
import africa.semicolon.election_management_system.data.constants.Role;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
public class RegisterCandidateRequest {
    private String name;
    private String identificationNumber;
    private String address;
    private LocalDate dob;
    private String stateOfOrigin;
    private String partyAffiliation;
    private Category positionContested;
    private Integer votingId;
    private Role role;
}
