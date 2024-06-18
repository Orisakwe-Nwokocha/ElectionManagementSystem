package africa.semicolon.election_management_system.data.repositories;

import africa.semicolon.election_management_system.data.models.Election;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectionRepository extends JpaRepository<Election, Long> {
}
