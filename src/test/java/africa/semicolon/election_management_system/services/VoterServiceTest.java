package africa.semicolon.election_management_system.services;

import africa.semicolon.election_management_system.data.models.Voter;
import africa.semicolon.election_management_system.data.repositories.ElectionRepository;
import africa.semicolon.election_management_system.dtos.requests.CastVoteRequest;
import africa.semicolon.election_management_system.dtos.requests.CreateVoterRequest;
import africa.semicolon.election_management_system.dtos.responses.CastVoteResponse;
import africa.semicolon.election_management_system.dtos.responses.CreateVoterResponse;
import africa.semicolon.election_management_system.exceptions.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Sql(scripts = {"/db/data.sql"})
public class VoterServiceTest {

    @Autowired
    private VoterService voterService;
    @Autowired
    private ElectionRepository electionRepository;


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

    @Test
    public void testVoterCanCastBallot(){
        CastVoteRequest castVoteRequest = buildCastVoteRequest();
        var election = electionRepository.findById(301L).orElseThrow();
        election.setStartDate(now().minusDays(1));
        election.setEndDate(now().plusDays(1));
        electionRepository.save(election);
        CastVoteResponse response = voterService.castVote(castVoteRequest);
        assertNotNull(response);
        assertThat(response.getMessage()).contains("success");
    }

    @Test
    @DisplayName("test that votes can only be cast when the election is open")
    public void electionIsNotYetOpenTest(){
        CastVoteRequest castVoteRequest = buildCastVoteRequest();
        assertThrows(UnauthorizedException.class, ()-> voterService.castVote(castVoteRequest));
    }

    @Test
    @DisplayName("test that votes can only be cast when the election is open")
    public void electionIsClosedTest(){
        CastVoteRequest castVoteRequest = buildCastVoteRequest();
        castVoteRequest.setElectionId(300L);
        assertThrows(UnauthorizedException.class, ()-> voterService.castVote(castVoteRequest));
    }

    private static CastVoteRequest buildCastVoteRequest() {
        CastVoteRequest castVoteRequest = new CastVoteRequest();
        castVoteRequest.setVotingId(654321L);
        castVoteRequest.setCandidateId(400L);
        castVoteRequest.setElectionId(301L);
        return castVoteRequest;
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


}
