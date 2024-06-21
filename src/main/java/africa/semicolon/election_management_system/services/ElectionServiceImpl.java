package africa.semicolon.election_management_system.services;

import africa.semicolon.election_management_system.data.models.Election;
import africa.semicolon.election_management_system.data.repositories.ElectionRepository;
import africa.semicolon.election_management_system.dtos.responses.ViewElectionResultsResponse;
import africa.semicolon.election_management_system.exceptions.ElectionNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElectionServiceImpl implements ElectionService {

    private final ElectionRepository electionRepository;

    public ElectionServiceImpl(ElectionRepository electionRepository) {

        this.electionRepository = electionRepository;
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
    public ViewElectionResultsResponse viewElectionResults(Long id) {
        return null;
    }
}
