package africa.semicolon.election_management_system.dtos.response;

import africa.semicolon.election_management_system.data.constants.Category;
import africa.semicolon.election_management_system.data.constants.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RegisterCandidateResponse {
    private Long id;
    private String name;
    private String identificationNumber;
    private String address;
    @JsonFormat(pattern = "dd/MMM/yyyy 'at' hh:mm a")
    private LocalDateTime dob;
    private String stateOfOrigin;
    private String partyAffiliation;
    private Category positionContested;
    @JsonProperty("voter_id")
    private Integer votingId;
    @JsonFormat(pattern = "dd/MMM/yyyy 'at' hh:mm a")
    private LocalDateTime dateRegistered;
    private String message;
}
