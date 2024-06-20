package africa.semicolon.election_management_system.services;

import africa.semicolon.election_management_system.data.models.Voter;
import africa.semicolon.election_management_system.data.repositories.VoteRepository;
import africa.semicolon.election_management_system.data.repositories.VoterRepository;
import africa.semicolon.election_management_system.dtos.requests.CastVoteRequest;
import africa.semicolon.election_management_system.dtos.requests.CreateVoterRequest;
import africa.semicolon.election_management_system.dtos.responses.CastVoteResponse;
import africa.semicolon.election_management_system.dtos.responses.CreateVoterResponse;
import africa.semicolon.election_management_system.exceptions.FailedVerificationException;
import africa.semicolon.election_management_system.exceptions.VoterNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

import static africa.semicolon.election_management_system.data.constants.Role.VOTER;

@Service
public class VoterServiceImpl implements VoterService{

    private final VoterRepository voterRepository;
    private final ModelMapper modelMapper;
    private final VoteRepository voteRepository;

    private final SecureRandom random = new SecureRandom();

    public VoterServiceImpl(VoterRepository voterRepository, VoteRepository voteRepository) {
        this.voterRepository = voterRepository;
        this.voteRepository = voteRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public CreateVoterResponse registerVoter(CreateVoterRequest request) {
        Voter voter = modelMapper.map(request, Voter.class);
        long randomId = generateRandomId();
        voter.setVotingId(randomId);
        voter.setIdentificationNumber("ID" + randomId);
        voter.setStatus(true);
        voter.setRole(VOTER);
        Voter savedVoter = voterRepository.save(voter);
        var response = modelMapper.map(savedVoter, CreateVoterResponse.class);
        response.setMessage("Voter registered successfully");
        return response;
    }

    @Override
    public Voter getVoterById(Long id) {
        return voterRepository.findById(id)
                .orElseThrow(()-> new VoterNotFoundException("Voter not found"));
    }

    @Override
    public Voter getVoterByVotingId(Long votingId){
        return voterRepository.findByVotingId(votingId) ;

    }

    @Override
    public CastVoteResponse castVote(CastVoteRequest castVoteRequest) {
        Voter voter = getVoterByVotingId(castVoteRequest.getVotingId());
        if (voter == null) throw new FailedVerificationException("");
        voteRepository.findByCandidateId(castVoteRequest.getCandidateId());
        voteRepository.findByElectionId(castVoteRequest.getElectionId());
        voter = voterRepository.save(voter);
        var response = modelMapper.map(voter, CastVoteResponse.class);
        response.setMessage("Voting casted successfully");
        return response;
    }

    private int generateRandomId() {
        return 100000 + random.nextInt(1000000);
    }
}
