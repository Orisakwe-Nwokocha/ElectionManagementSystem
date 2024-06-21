package africa.semicolon.election_management_system.services;

import africa.semicolon.election_management_system.dtos.requests.RegisterCandidateRequest;
import africa.semicolon.election_management_system.dtos.responses.RegisterCandidateResponse;
import africa.semicolon.election_management_system.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;

import static africa.semicolon.election_management_system.data.constants.Category.NATIONAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql("/db/data.sql")
class CandidateServiceTest {

    @Autowired
    private CandidateService candidateService;


    @Test
    public void registerCandidateTest() {
        RegisterCandidateRequest request = buildRequest();
        request.setElectionId(300L);
        RegisterCandidateResponse response = candidateService.registerCandidate(request);
        System.out.println(response);
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).contains("success");
    }

    @Test
    @DisplayName("test that candidate cannot register outside a scheduled election")
    public void registerCandidateTest2() {
        RegisterCandidateRequest request = buildRequest();
        assertThrows(ResourceNotFoundException.class, ()-> candidateService.registerCandidate(request));
        request.setElectionId(200L);
        assertThrows(ResourceNotFoundException.class, ()-> candidateService.registerCandidate(request));
    }

    private static RegisterCandidateRequest buildRequest() {
        RegisterCandidateRequest request = new RegisterCandidateRequest();
        request.setName("John");
        request.setAddress("no 29 adewale str");
        request.setIdentificationNumber("12343487443");
        request.setPassword("password");
        request.setDateOfBirth(LocalDate.of(1980, 10, 2));
        request.setPartyAffiliation("P.D.P");
        request.setPositionContested(NATIONAL);
        request.setStateOfOrigin("Benue");
        return request;
    }
}