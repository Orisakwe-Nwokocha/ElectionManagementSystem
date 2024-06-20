package africa.semicolon.election_management_system.data.repositories;

import africa.semicolon.election_management_system.data.models.Vote;

import africa.semicolon.election_management_system.data.models.Voter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Vote findByCandidateId(Long candidateId);

    Vote findByElectionId(Long electionId);
}
