package africa.semicolon.election_management_system.services;

import africa.semicolon.election_management_system.dtos.requests.RegisterAdminRequest;
import africa.semicolon.election_management_system.dtos.requests.ScheduleElectionRequest;
import africa.semicolon.election_management_system.dtos.responses.RegisterAdminResponse;
import africa.semicolon.election_management_system.dtos.responses.ScheduleElectionResponse;
import jdk.jfr.Registered;
import org.springframework.scheduling.annotation.Scheduled;

public interface AdminService {
    RegisterAdminResponse register(RegisterAdminRequest request);
    ScheduleElectionResponse schedule(ScheduleElectionRequest request);
}
