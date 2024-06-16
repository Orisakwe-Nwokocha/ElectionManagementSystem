package africa.semicolon.election_management_system.data.repositories;

import africa.semicolon.election_management_system.data.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
