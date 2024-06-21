package africa.semicolon.election_management_system.services;

import africa.semicolon.election_management_system.data.models.Voter;
import africa.semicolon.election_management_system.data.repositories.ElectionRepository;
import africa.semicolon.election_management_system.dtos.requests.CastVoteRequest;
import africa.semicolon.election_management_system.dtos.requests.CreateVoterRequest;
import africa.semicolon.election_management_system.dtos.responses.CastVoteResponse;
import africa.semicolon.election_management_system.dtos.responses.CreateVoterResponse;
import africa.semicolon.election_management_system.exceptions.IneligibleToVoteException;
import africa.semicolon.election_management_system.exceptions.InvalidVoteException;
import africa.semicolon.election_management_system.exceptions.UnauthorizedException;
import africa.semicolon.election_management_system.exceptions.IdentificationNumberAlreadyExistsException;
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
    @DisplayName("test that a voter below 18 years cannot register")
    public void testThatIneligibleVoterCannotRegister(){
        CreateVoterRequest request = buildCreateIneligibleVoterRequest();
        assertThrows(IneligibleToVoteException.class, ()->voterService.registerVoter(request));
    }
    @Test
    @DisplayName("test that only a voter with a unique voter ID can register")
    public void testThatOnlyVotersWithUniqueVoterIdCanRegister(){
        CreateVoterRequest request = buildCreateVoterRequest();
        voterService.registerVoter(request);
        assertThrows(IdentificationNumberAlreadyExistsException.class, ()->voterService.registerVoter(request));

    }

    @Test
    public void testVoterCanCastBallot(){
        CastVoteRequest castVoteRequest = buildCastVoteRequest();
        updateElection();
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

    @Test
    @DisplayName("test that voter cannot vote for ineligible candidate")
    public void voteCastingTest() {
        CastVoteRequest request = buildCastVoteRequest();
        updateElection();
        request.setCandidateId(401L);
        assertThrows(InvalidVoteException.class, ()-> voterService.castVote(request));
    }

    @Test
    @DisplayName("test that voter cannot vote twice")
    public void voteCastingTest2() {
        CastVoteRequest request = buildCastVoteRequest();
        updateElection();
        CastVoteResponse response = voterService.castVote(request);
        assertNotNull(response);
        assertThat(response.getMessage()).contains("success");
        try {
            CastVoteResponse response2 = voterService.castVote(request);
            assertThat(response2).isNull();
        } catch (InvalidVoteException exception) {
            assertThat(exception).isNotNull();
            assertThat(exception.getMessage()).contains("already");
        }
    }

    private void updateElection() {
        var election = electionRepository.findById(301L).orElseThrow();
        election.setStartDate(now().minusDays(1));
        election.setEndDate(now().plusDays(1));
        electionRepository.save(election);
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
        request.setIdentificationNumber("34567891");
        request.setDateOfBirth(LocalDate.of(1990, 1, 1));
        request.setStateOfOrigin("Lagos");
        return request;
    }

    private static CreateVoterRequest buildCreateIneligibleVoterRequest() {
        CreateVoterRequest request = new CreateVoterRequest();
        request.setName("John Doe");
        request.setPassword("password");
        request.setAddress("123 Main St");
        request.setIdentificationNumber("34567891");
        request.setDateOfBirth(LocalDate.of(2008, 1, 1));
        request.setStateOfOrigin("Lagos");
        return request;
    }


}
