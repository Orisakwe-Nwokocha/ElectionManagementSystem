package africa.semicolon.election_management_system.data.models;

import africa.semicolon.election_management_system.data.constants.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "elections")
@Getter
@Setter
public class Election {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String title;
    @Enumerated(STRING)
    private Category category;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
