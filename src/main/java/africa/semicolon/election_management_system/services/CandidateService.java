package africa.semicolon.election_management_system.services;

import africa.semicolon.election_management_system.data.models.Candidate;
import africa.semicolon.election_management_system.dtos.requests.RegisterCandidateRequest;
import africa.semicolon.election_management_system.dtos.responses.RegisterCandidateResponse;

import java.util.List;

public interface CandidateService {
    RegisterCandidateResponse registerCandidate(RegisterCandidateRequest request);
    Candidate getCandidateBy(Long id);
    List<Candidate> getCandidatesFor(Long electionId);
}
