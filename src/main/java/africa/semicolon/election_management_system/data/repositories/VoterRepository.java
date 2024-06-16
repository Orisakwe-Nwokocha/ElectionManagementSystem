package africa.semicolon.election_management_system.data.repositories;

import africa.semicolon.election_management_system.data.models.Voter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoterRepository extends JpaRepository<Voter, Long> {
}
