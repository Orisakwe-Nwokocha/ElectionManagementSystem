package africa.semicolon.election_management_system.services;

import africa.semicolon.election_management_system.data.models.Admin;
import africa.semicolon.election_management_system.dtos.requests.RegisterAdminRequest;
import africa.semicolon.election_management_system.dtos.requests.ScheduleElectionRequest;
import africa.semicolon.election_management_system.dtos.requests.UpdateVoterRequest;
import africa.semicolon.election_management_system.dtos.responses.RegisterAdminResponse;
import africa.semicolon.election_management_system.dtos.responses.ScheduleElectionResponse;
import africa.semicolon.election_management_system.dtos.responses.UpdateVoterResponse;

public interface AdminService {
    RegisterAdminResponse register(RegisterAdminRequest request);
    ScheduleElectionResponse schedule(ScheduleElectionRequest request);
    Admin getAdminBy(Long id);
    UpdateVoterResponse updateVoter(UpdateVoterRequest request, AdminService adminService);
}
