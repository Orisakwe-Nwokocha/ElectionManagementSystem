package africa.semicolon.election_management_system.services;


import africa.semicolon.election_management_system.data.models.Admin;
import africa.semicolon.election_management_system.data.repositories.AdminRepository;
import africa.semicolon.election_management_system.dtos.requests.RegisterAdminRequest;
import africa.semicolon.election_management_system.dtos.requests.ScheduleElectionRequest;
import africa.semicolon.election_management_system.dtos.responses.RegisterAdminResponse;
import africa.semicolon.election_management_system.dtos.responses.ScheduleElectionResponse;
import africa.semicolon.election_management_system.exceptions.AdminRegistrationException;
import africa.semicolon.election_management_system.exceptions.ElectionManagementSystemBaseException;
import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final ModelMapper modelMapper;


    public AdminServiceImpl(AdminRepository adminRepository, ModelMapper modelMapper) {
        this.adminRepository = adminRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public RegisterAdminResponse register(RegisterAdminRequest request) {
            String username = request.getUsername().toLowerCase();
            validate(username.toLowerCase());
            request.setUsername(username);
            Admin admin = modelMapper.map(request, Admin.class);
            admin = adminRepository.save(admin);
            var adminResponse = modelMapper.map(admin, RegisterAdminResponse.class);
            adminResponse.setMessage("User registered successfully");
            return adminResponse;
        }
    private void validate(String username) {
        if (username == null || username.isEmpty()) {
            throw new ElectionManagementSystemBaseException("Username cannot be null or empty");
        }
        String existingUser = username.toLowerCase();
        if (adminRepository.existsByUsername(existingUser)) {
            throw new ElectionManagementSystemBaseException(existingUser + " already exists");
        }
    }



    @Override
    public ScheduleElectionResponse schedule(ScheduleElectionRequest request) {
        return null;
    }


}
