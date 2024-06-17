package africa.semicolon.election_management_system.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "voters")
@Getter
@Setter
public class Voter {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;


}
