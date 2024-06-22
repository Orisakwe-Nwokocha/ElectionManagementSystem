package africa.semicolon.election_management_system.services;

import africa.semicolon.election_management_system.data.models.Candidate;
import africa.semicolon.election_management_system.data.models.Election;
import africa.semicolon.election_management_system.data.repositories.ElectionRepository;
import africa.semicolon.election_management_system.data.repositories.VoteRepository;
import africa.semicolon.election_management_system.dtos.responses.CandidateResultResponse;
import africa.semicolon.election_management_system.dtos.responses.ViewElectionResultsResponse;
import africa.semicolon.election_management_system.exceptions.ElectionNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElectionServiceImpl implements ElectionService {

    private final ElectionRepository electionRepository;
    private final VoteRepository voteRepository;
    private final ModelMapper modelMapper;

    private CandidateService candidateService;

    public ElectionServiceImpl(ElectionRepository electionRepository, VoteRepository voteRepository, ModelMapper modelMapper) {
        this.electionRepository = electionRepository;
        this.voteRepository = voteRepository;
        this.modelMapper = modelMapper;
    }

    @Autowired
    @Lazy
    public void setCandidateService(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @Override
    public Election getElectionBy(Long id) {
        return electionRepository.findById(id)
                .orElseThrow(()-> new ElectionNotFoundException("Election not found"));
    }

    @Override
    public List<Election> getAllElections() {
        return electionRepository.findAll();
    }

    @Override
    public ViewElectionResultsResponse viewElectionResults(Long electionId) {
        if (!electionRepository.existsById(electionId)) throw new ElectionNotFoundException("Election not found");
        var candidates = candidateService.getCandidatesFor(electionId);
        ViewElectionResultsResponse response = new ViewElectionResultsResponse();
        candidates.forEach(candidate -> {
            CandidateResultResponse candidateResponse = getCandidateResponse(candidate, electionId);
            response.getCandidateResultResponses().add(candidateResponse);
        });
        return response;
    }

    private CandidateResultResponse getCandidateResponse(Candidate candidate, Long electionId) {
        CandidateResultResponse candidateResponse = modelMapper.map(candidate, CandidateResultResponse.class);
        var votes = voteRepository.findVotesByCandidateAndElection(candidate.getId(), electionId);
        candidateResponse.setNumberOfVotes((long) votes.size());
        return candidateResponse;
    }

}
