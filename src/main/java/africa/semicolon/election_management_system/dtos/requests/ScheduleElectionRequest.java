package africa.semicolon.election_management_system.dtos.requests;

import africa.semicolon.election_management_system.data.constants.Category;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ScheduleElectionRequest {
    private String title;
    private Category category;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
