package africa.semicolon.election_management_system.services;

import africa.semicolon.election_management_system.data.models.Candidate;
import africa.semicolon.election_management_system.data.models.Election;
import africa.semicolon.election_management_system.data.repositories.CandidateRepository;
import africa.semicolon.election_management_system.dtos.requests.CreateVoterRequest;
import africa.semicolon.election_management_system.dtos.requests.RegisterCandidateRequest;
import africa.semicolon.election_management_system.dtos.responses.CreateVoterResponse;
import africa.semicolon.election_management_system.dtos.responses.RegisterCandidateResponse;
import africa.semicolon.election_management_system.exceptions.CandidateNotFoundException;
import africa.semicolon.election_management_system.exceptions.ElectionNotFoundException;
import africa.semicolon.election_management_system.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CandidateServiceImpl implements CandidateService{

   private final CandidateRepository candidateRepository;
   private final ModelMapper modelMapper;

   private VoterService voterService;
   private ElectionService electionService;

    public CandidateServiceImpl(CandidateRepository candidateRepository, ModelMapper modelMapper){
        this.candidateRepository = candidateRepository;
        this.modelMapper = modelMapper;
    }

    @Autowired
    @Lazy
    public void setVoterService(VoterService voterService) {
        this.voterService = voterService;
    }

    @Autowired
    @Lazy
    public void setElectionService(ElectionService electionService) {
        this.electionService = electionService;
    }

    @Override
    @Transactional
    public RegisterCandidateResponse registerCandidate(RegisterCandidateRequest request) {
        Election election = getElection(request.getElectionId());
        CreateVoterResponse createVoterResponse = registerVoter(request);
        Candidate candidate =  modelMapper.map(request, Candidate.class);
        candidate.setVotingId(createVoterResponse.getVotingId());
        candidate.setElection(election);
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

    @Override
    public List<Candidate> getCandidatesFor(Long electionId) {
        return candidateRepository.findAllCandidatesFor(electionId);
    }

    private Election getElection(Long electionId) {
        if (electionId == null) throw new ResourceNotFoundException("Election has not yet been scheduled");
        try {
           return electionService.getElectionBy(electionId);
        } catch (ElectionNotFoundException exception) {
            throw new ResourceNotFoundException("Election has not yet been scheduled");
        }
    }

    private CreateVoterResponse registerVoter(RegisterCandidateRequest request) {
        CreateVoterRequest createVoterRequest = modelMapper.map(request, CreateVoterRequest.class);
        return voterService.registerVoter(createVoterRequest);
    }
}
