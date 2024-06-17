package africa.semicolon.election_management_system.data.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "votes")
@Getter
@Setter
public class Vote {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @ManyToOne
    private Voter voter;
    @ManyToOne
    private Election election;
    @OneToOne
    private Candidate candidate;
}
