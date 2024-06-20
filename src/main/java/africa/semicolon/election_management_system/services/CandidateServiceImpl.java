package africa.semicolon.election_management_system.services;

import africa.semicolon.election_management_system.data.models.Candidate;
import africa.semicolon.election_management_system.data.repositories.CandidateRepository;
import africa.semicolon.election_management_system.dtos.requests.CreateVoterRequest;
import africa.semicolon.election_management_system.dtos.requests.RegisterCandidateRequest;
import africa.semicolon.election_management_system.dtos.responses.CreateVoterResponse;
import africa.semicolon.election_management_system.dtos.responses.RegisterCandidateResponse;
import africa.semicolon.election_management_system.exceptions.CandidateNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class CandidateServiceImpl implements CandidateService{

   private final CandidateRepository candidateRepository;
   private final ModelMapper modelMapper;

   private VoterService voterService;

    public CandidateServiceImpl(CandidateRepository candidateRepository, ModelMapper modelMapper){
        this.candidateRepository = candidateRepository;
        this.modelMapper = modelMapper;
    }

    @Autowired
    @Lazy
    public void setVoterService(VoterService voterService) {
        this.voterService = voterService;
    }

    @Override
    public RegisterCandidateResponse registerCandidate(RegisterCandidateRequest request){
        Candidate candidate =  modelMapper.map(request, Candidate.class);
        candidate = candidateRepository.save(candidate);
        CreateVoterRequest createVoterRequest = modelMapper.map(candidate, CreateVoterRequest.class);
        CreateVoterResponse createVoterResponse = voterService.registerVoter(createVoterRequest);
        candidate.setVotingId(createVoterResponse.getVotingId());
        candidate = candidateRepository.save(candidate);
        var response = modelMapper.map(candidate, RegisterCandidateResponse.class);
        response.setMessage("Candidate registered successfully");
        return response;
    }

    @Override
    public Candidate getCandidateBy(Long id){
        return candidateRepository.findById(id)
                .orElseThrow(()-> new CandidateNotFoundException("Candidate not found"));
    }
}
