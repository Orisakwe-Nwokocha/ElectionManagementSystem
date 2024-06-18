package africa.semicolon.election_management_system.data.models;

import africa.semicolon.election_management_system.data.constants.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "candidates")
@Getter
@Setter
public class Candidate {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    private String password;
    private String address;
    private LocalDate dob;
    private String stateOfOrigin;
    private String partyAffiliation;
    private Category positionContested;
    private String identificationNumber;

}
