package africa.semicolon.election_management_system.services;

import africa.semicolon.election_management_system.data.models.Voter;
import africa.semicolon.election_management_system.dtos.requests.CastVoteRequest;
import africa.semicolon.election_management_system.dtos.requests.CreateVoterRequest;
import africa.semicolon.election_management_system.dtos.responses.CastVoteResponse;
import africa.semicolon.election_management_system.dtos.responses.CreateVoterResponse;
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
@Sql(scripts = {"/db/data.sql"})
public class VoterServiceTest {

    @Autowired
    private VoterService voterService;

    @Test
    public void testThatVoterCanRegister(){
        CreateVoterRequest request = buildCreateVoterRequest();
        CreateVoterResponse response = voterService.registerVoter(request);
        log.info("Register Voter Response: {}", response);
        assertNotNull(response);
        assertNotNull(response.getId());
        assertTrue(response.getMessage().contains("successful"));
        assertNotNull(response.getIdentificationNumber());
        assertNotNull(response.getDateRegistered());
        Voter savedVoter = voterService.getVoterById(response.getId());
        assertNotNull(savedVoter);
        assertThat(savedVoter.getVotingId()).isBetween(100000L, 1000000L);
        assertTrue(savedVoter.getStatus());
    }

    private static CreateVoterRequest buildCreateVoterRequest() {
        CreateVoterRequest request = new CreateVoterRequest();
        request.setName("John Doe");
        request.setPassword("password");
        request.setAddress("123 Main St");
        request.setDateOfBirth(LocalDate.of(1990, 1, 1));
        request.setStateOfOrigin("Lagos");
        return request;
    }

    @Test
    public void testVoterCanCastBallot(){
        CastVoteRequest castVoteRequest = new CastVoteRequest();
        buildCastVoteRequest(castVoteRequest);
        CastVoteResponse response = voterService.castVote(castVoteRequest);
        assertNotNull(response);
        assertNotNull(response.getVotingId());
        assertNotNull(response.getDateRegistered());
        assertNotNull(response.getMessage());

    }

    private static void buildCastVoteRequest(CastVoteRequest castVoteRequest) {
        castVoteRequest.setVotingId(100L);
        castVoteRequest.setCandidateId(1L);
        castVoteRequest.setElectionId(1L);
    }


}
