package africa.semicolon.election_management_system.services;
import africa.semicolon.election_management_system.data.models.Voter;
import africa.semicolon.election_management_system.dtos.request.CreateVoterRequest;
import africa.semicolon.election_management_system.dtos.response.*;

import java.util.Optional;

public interface VoterService {

    CreateVoterResponse registerVoter(CreateVoterRequest request);

    Voter getVoterById(Long id);
}

