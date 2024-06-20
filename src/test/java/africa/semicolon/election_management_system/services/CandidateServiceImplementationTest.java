package africa.semicolon.election_management_system.services;

import africa.semicolon.election_management_system.data.repositories.CandidateRepository;
import africa.semicolon.election_management_system.dtos.requests.RegisterCandidateRequest;
import africa.semicolon.election_management_system.dtos.response.RegisterCandidateResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static africa.semicolon.election_management_system.data.constants.Category.NATIONAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootApplication
class CandidateServiceImplementationTest {
    @Autowired
    private CandidateService candidateService;
    @Autowired
    private CandidateRepository candidateRepository;

    @Test
    public void registerCandidate() {
        RegisterCandidateRequest request = new RegisterCandidateRequest();
        request.setName("John");
        request.setAddress("no 29 adewale str");
        request.setIdentificationNumber("2024/20/06");
        request.setPartyAffiliation("P.D.P");
        request.setPositionContested(NATIONAL);
        request.setStateOfOrigin("Benue");
        request.setIdentificationNumber("12-3434-87443");
        request.setVotingId(2);
        RegisterCandidateResponse response = candidateService.registerCandidate(request);
        assertThat(candidateRepository.findAll().size()).isEqualTo(1);
        Assertions.assertThat(response).isNotNull();
    }
}