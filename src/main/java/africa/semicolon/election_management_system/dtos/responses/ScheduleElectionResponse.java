package africa.semicolon.election_management_system.dtos.responses;

import africa.semicolon.election_management_system.data.constants.Category;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScheduleElectionResponse {
    private Long electionId;
    private Category category;
    private LocalDateTime timeCreated;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String message;
}
