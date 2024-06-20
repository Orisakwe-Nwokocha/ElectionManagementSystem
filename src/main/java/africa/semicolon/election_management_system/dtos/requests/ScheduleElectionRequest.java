package africa.semicolon.election_management_system.dtos.requests;

import africa.semicolon.election_management_system.data.constants.Category;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScheduleElectionRequest {
    private String title;
    private Category category;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
