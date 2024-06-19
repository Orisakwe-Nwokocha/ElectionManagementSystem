package africa.semicolon.election_management_system.services;

import africa.semicolon.election_management_system.data.models.Voter;
import africa.semicolon.election_management_system.data.repositories.VoterRepository;
import africa.semicolon.election_management_system.dtos.request.CreateVoterRequest;
import africa.semicolon.election_management_system.dtos.response.CreateVoterResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class VoterServiceImpl implements VoterService{

    private final VoterRepository voterRepository;
    @Autowired
    private ModelMapper modelMapper;
    private final SecureRandom random = new SecureRandom();

    public VoterServiceImpl(VoterRepository voterRepository) {
        this.voterRepository = voterRepository;
    }

    @Override
    public CreateVoterResponse registerVoter(CreateVoterRequest request) {
        Voter voter = modelMapper.map(request, Voter.class);
        int randomId = generateRandomId();
        voter.setVotingId(randomId);
        voter.setIdentificationNumber("ID" + randomId);
        voter.setStatus(true);
        Voter savedVoter = voterRepository.save(voter);
        var response = modelMapper.map(savedVoter, CreateVoterResponse.class);
        response.setMessage("Voter registered successfully");
        return response;
    }

    @Override
    public Voter getVoterById(Long id) {
        return voterRepository.findById(id).orElse(null);
    }
    private int generateRandomId() {
        return 100000 + random.nextInt(900000);
    }
}
