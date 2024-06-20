package africa.semicolon.election_management_system.data.repositories;

import africa.semicolon.election_management_system.data.models.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
}
