package africa.semicolon.election_management_system.services;

import africa.semicolon.election_management_system.dtos.requests.RegisterCandidateRequest;
import africa.semicolon.election_management_system.dtos.response.RegisterCandidateResponse;
import org.springframework.stereotype.Service;

public interface CandidateService {
    RegisterCandidateResponse registerCandidate(RegisterCandidateRequest request);
}
