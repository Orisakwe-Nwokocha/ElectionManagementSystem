package africa.semicolon.election_management_system.services;

import africa.semicolon.election_management_system.data.models.Candidate;
import africa.semicolon.election_management_system.data.models.Election;
import africa.semicolon.election_management_system.data.models.Vote;
import africa.semicolon.election_management_system.data.models.Voter;
import africa.semicolon.election_management_system.data.repositories.VoteRepository;
import africa.semicolon.election_management_system.data.repositories.VoterRepository;
import africa.semicolon.election_management_system.dtos.requests.CastVoteRequest;
import africa.semicolon.election_management_system.dtos.requests.CreateVoterRequest;
import africa.semicolon.election_management_system.dtos.responses.CastVoteResponse;
import africa.semicolon.election_management_system.dtos.responses.CreateVoterResponse;
import africa.semicolon.election_management_system.exceptions.FailedVerificationException;
import africa.semicolon.election_management_system.exceptions.InvalidVoteException;
import africa.semicolon.election_management_system.exceptions.UnauthorizedException;
import africa.semicolon.election_management_system.exceptions.VoterNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Objects;

import static africa.semicolon.election_management_system.data.constants.Role.VOTER;
import static java.time.LocalDateTime.now;

@Service
public class VoterServiceImpl implements VoterService{

    private final VoterRepository voterRepository;
    private final ModelMapper modelMapper;
    private final VoteRepository voteRepository;

    private ElectionService electionService;
    private CandidateService candidateService;


    private final SecureRandom random = new SecureRandom();

    public VoterServiceImpl(VoterRepository voterRepository, VoteRepository voteRepository) {
        this.voterRepository = voterRepository;
        this.voteRepository = voteRepository;
        this.modelMapper = new ModelMapper();
    }

    @Autowired
    @Lazy
    public void setElectionService(ElectionService electionService) {
        this.electionService = electionService;
    }

    @Autowired
    @Lazy
    public void setCandidateService(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @Override
    public CreateVoterResponse registerVoter(CreateVoterRequest request) {
        Voter voter = modelMapper.map(request, Voter.class);
        Long randomId = generateRandomId();
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
    public CastVoteResponse castVote(CastVoteRequest castVoteRequest) {
        Election election = electionService.getElectionBy(castVoteRequest.getElectionId());
        Candidate candidate = candidateService.getCandidateBy(castVoteRequest.getCandidateId());
        validate(election, candidate);
        Long votingId = castVoteRequest.getVotingId();
        Voter voter = voterRepository.findByVotingId(votingId)
                .orElseThrow(()-> new FailedVerificationException(
                        String.format("Voter could not be verified with %s", votingId)));
        validate(voter, election);
        Vote newVote = buildVote(voter, candidate, election);
        newVote = voteRepository.save(newVote);
        CastVoteResponse response = modelMapper.map(newVote, CastVoteResponse.class);
        response.setMessage("Voting casted successfully");
        return response;
    }

    private void validate(Voter voter, Election election) {
        var votes = voteRepository.findVotesByVoterAndElection(voter.getId(), election.getId());
        System.out.println(votes);
        if (!votes.isEmpty()) throw new InvalidVoteException("Vote has already been casted");
    }

    private void validate(Election election, Candidate candidate) {
        var startDate = election.getStartDate();
        var endDate = election.getEndDate();
        var currentDate = now();
        if (currentDate.isBefore(startDate) || currentDate.isAfter(endDate))
            throw new UnauthorizedException("Election is not open");
        if (!Objects.equals(election, candidate.getElection()))
            throw new InvalidVoteException("Selected candidate is not eligible for the selected election");
    }

    private static Vote buildVote(Voter voter, Candidate candidate, Election election) {
        Vote newVote = new Vote();
        newVote.setVoter(voter);
        newVote.setCandidate(candidate);
        newVote.setElection(election);
        return newVote;
    }

    private long generateRandomId() {
        return random.nextLong(100000,1000000);
    }
}
