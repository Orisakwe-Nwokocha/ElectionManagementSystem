package africa.semicolon.election_management_system.data.repositories;

import africa.semicolon.election_management_system.data.models.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
}
