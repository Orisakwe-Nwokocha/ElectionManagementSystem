package africa.semicolon.election_management_system.services;
import africa.semicolon.election_management_system.data.models.Voter;
import africa.semicolon.election_management_system.dtos.requests.CreateVoterRequest;
import africa.semicolon.election_management_system.dtos.responses.CreateVoterResponse;

public interface VoterService {

    CreateVoterResponse registerVoter(CreateVoterRequest request);

    Voter getVoterById(Long id);
}

