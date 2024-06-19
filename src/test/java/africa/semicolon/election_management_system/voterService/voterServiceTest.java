package africa.semicolon.election_management_system.voterService;

import africa.semicolon.election_management_system.data.constants.Role;
import africa.semicolon.election_management_system.data.models.Voter;
import africa.semicolon.election_management_system.data.repositories.VoterRepository;
import africa.semicolon.election_management_system.dtos.request.CreateVoterRequest;
import africa.semicolon.election_management_system.dtos.response.CreateVoterResponse;
import africa.semicolon.election_management_system.services.VoterService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Slf4j
//@Sql(scripts = {"/db/data.sql"})
public class voterServiceTest {
    @Autowired
    private VoterService voterService;
    @Autowired
    private VoterRepository voterRepository;

    @Test
    public void testThatVoterCanRegister(){
        CreateVoterRequest request = new CreateVoterRequest();
        request.setName("John Doe");
        request.setPassword("password");
        request.setAddress("123 Main St");
        request.setDateOfBirth(LocalDate.of(1990, 1, 1));
        request.setStateOfOrigin("Lagos");
        request.setRole(Role.VOTER);

        CreateVoterResponse response = voterService.registerVoter(request);
        log.info("Register Voter Response: {}", response);
        assertNotNull(response);
        assertNotNull(response.getId());
        assertTrue(response.getMessage().contains("successful"));
        assertNotNull(response.getIdentificationNumber());
        assertNotNull(response.getDateRegistered());

        Voter savedVoter = voterService.getVoterById(response.getId());
        assertNotNull(savedVoter);
        assertThat(savedVoter.getVotingId()).isBetween(100000, 999999);
        assertTrue(savedVoter.getStatus());

    }

}
