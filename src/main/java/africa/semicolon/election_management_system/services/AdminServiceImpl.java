package africa.semicolon.election_management_system.services;

import africa.semicolon.election_management_system.data.models.Admin;
import africa.semicolon.election_management_system.data.models.Election;
import africa.semicolon.election_management_system.data.repositories.AdminRepository;
import africa.semicolon.election_management_system.data.repositories.ElectionRepository;
import africa.semicolon.election_management_system.dtos.requests.RegisterAdminRequest;
import africa.semicolon.election_management_system.dtos.requests.ScheduleElectionRequest;
import africa.semicolon.election_management_system.dtos.responses.RegisterAdminResponse;
import africa.semicolon.election_management_system.dtos.responses.ScheduleElectionResponse;
import africa.semicolon.election_management_system.exceptions.AdminNotFoundException;
import africa.semicolon.election_management_system.exceptions.ElectionManagementSystemBaseException;
import africa.semicolon.election_management_system.exceptions.UsernameExistsException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static java.time.LocalDateTime.now;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final ElectionRepository electionRepository;
    private final ModelMapper modelMapper;

    public AdminServiceImpl(AdminRepository adminRepository, ElectionRepository electionRepository, ModelMapper modelMapper) {
        this.adminRepository = adminRepository;
        this.electionRepository = electionRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public RegisterAdminResponse register(RegisterAdminRequest request) {
        String username = request.getUsername();
        validate(username);
        request.setUsername(username.toLowerCase());
        Admin admin = modelMapper.map(request, Admin.class);
        admin = adminRepository.save(admin);
        var adminResponse = modelMapper.map(admin, RegisterAdminResponse.class);
        adminResponse.setMessage("User registered successfully");
        return adminResponse;
    }

    @Override
    public ScheduleElectionResponse schedule(ScheduleElectionRequest request) {
        Election election = modelMapper.map(request, Election.class);
        election = electionRepository.save(election);
        var electionResponse = modelMapper.map(election, ScheduleElectionResponse.class);
        electionResponse.setTimeCreated(now());
        electionResponse.setMessage("Election scheduled successfully");
        return electionResponse;
    }

    @Override
    public Admin getAdminBy(Long id) {
        return adminRepository.findById(id)
                .orElseThrow(()-> new AdminNotFoundException("Admin not found"));
    }

    private void validate(String username) {
        if (username == null || username.isEmpty()) {
            throw new ElectionManagementSystemBaseException("Username cannot be null or empty");
        }
        boolean usernameExists = adminRepository.existsByUsername(username.toLowerCase());
        if (usernameExists) throw new UsernameExistsException("Username already taken");
    }

}
