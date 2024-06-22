package africa.semicolon.election_management_system.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class UpdateVoterResponse {
    private String address;
    private String name;
    private String identificationNumber;
    private Long votingId;
    private String message;
    @JsonFormat(pattern = "dd/MMM/yyyy 'at' hh:mm a")
    private LocalDateTime dateUpdated = LocalDateTime.now();
}
