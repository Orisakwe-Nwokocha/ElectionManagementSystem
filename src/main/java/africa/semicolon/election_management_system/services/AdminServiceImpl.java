package africa.semicolon.election_management_system.services;


import africa.semicolon.election_management_system.data.models.Admin;
import africa.semicolon.election_management_system.data.repositories.AdminRepository;
import africa.semicolon.election_management_system.dtos.requests.RegisterAdminRequest;
import africa.semicolon.election_management_system.dtos.requests.ScheduleElectionRequest;
import africa.semicolon.election_management_system.dtos.responses.RegisterAdminResponse;
import africa.semicolon.election_management_system.dtos.responses.ScheduleElectionResponse;
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
        Admin admin = modelMapper.map(request, Admin.class);
        admin = adminRepository.save(admin);
        var adminResponse = modelMapper.map(admin, RegisterAdminResponse.class);
        adminResponse.setMessage("user registered successfully");
        return adminResponse;
    }

    @Override
    public ScheduleElectionResponse schedule(ScheduleElectionRequest request) {
        return null;
    }
}
