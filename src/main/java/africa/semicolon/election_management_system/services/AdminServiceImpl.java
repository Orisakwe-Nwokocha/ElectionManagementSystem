package africa.semicolon.election_management_system.services;


import africa.semicolon.election_management_system.dtos.requests.RegisterAdminRequest;
import africa.semicolon.election_management_system.dtos.requests.ScheduleElectionRequest;
import africa.semicolon.election_management_system.dtos.responses.RegisterAdminResponse;
import africa.semicolon.election_management_system.dtos.responses.ScheduleElectionResponse;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Override
    public RegisterAdminResponse register(RegisterAdminRequest request) {
        return null;
    }

    @Override
    public ScheduleElectionResponse schedule(ScheduleElectionRequest request) {
        return null;
    }
}
