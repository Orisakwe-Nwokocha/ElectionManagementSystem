package africa.semicolon.election_management_system.services;

import africa.semicolon.election_management_system.data.models.Admin;
import africa.semicolon.election_management_system.data.models.Candidate;
import africa.semicolon.election_management_system.data.models.Election;
import africa.semicolon.election_management_system.data.repositories.AdminRepository;
import africa.semicolon.election_management_system.data.repositories.CandidateRepository;
import africa.semicolon.election_management_system.data.repositories.ElectionRepository;
import africa.semicolon.election_management_system.data.repositories.VoterRepository;
import africa.semicolon.election_management_system.dtos.requests.*;
import africa.semicolon.election_management_system.dtos.responses.*;
import africa.semicolon.election_management_system.exceptions.AdminNotFoundException;
import africa.semicolon.election_management_system.exceptions.ElectionManagementSystemBaseException;
import africa.semicolon.election_management_system.exceptions.UsernameExistsException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.time.LocalDateTime.now;

@Service
public class
AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final ElectionRepository electionRepository;
    private final CandidateService candidateService;
    private final ModelMapper modelMapper;

    public AdminServiceImpl(AdminRepository adminRepository, ElectionRepository electionRepository, CandidateService candidateService,  ModelMapper modelMapper) {
        this.adminRepository = adminRepository;
        this.electionRepository = electionRepository;
        this.candidateService = candidateService;
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
    public RegisterCandidateResponse registerCandidate(RegisterCandidateRequest request) {
        return candidateService.registerCandidate(request);
    }

    @Override
    public Candidate getCandidateBy(Long id) {
        return candidateService.getCandidateBy(id);
    }

    @Override
    public List<Candidate> getCandidatesFor(Long electionId) {
        return candidateService.getCandidatesFor(electionId);
    }
    @Override
    public DeleteCandidateResponse deleteCandidate(Candidate candidate) {
        return getDeleteCandidateResponse(candidate);
    }
    private DeleteCandidateResponse getDeleteCandidateResponse(Candidate candidate) {
        if (candidate == null) {
            DeleteCandidateResponse deleteCandidateResponse = new DeleteCandidateResponse();
            deleteCandidateResponse.setMessage("Candidate not found");
            return deleteCandidateResponse;
        }
        candidateService.deleteCandidate(candidate);
        DeleteCandidateResponse deleteCandidateResponse = new DeleteCandidateResponse();
        deleteCandidateResponse.setMessage("Candidate deleted successfully");
        return deleteCandidateResponse;
    }


    @Override
    public UpdateCandidateResponse updateCandidate(UpdateCandidateRequest updateCandidateRequest) {
        return candidateService.updateCandidate(updateCandidateRequest);
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
