package africa.semicolon.election_management_system.controllers;

import africa.semicolon.election_management_system.data.constants.Category;
import africa.semicolon.election_management_system.dtos.requests.RegisterCandidateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts ={"/db/data.sql"})
public class CandidateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testRegisterCandidateTest() throws Exception {
        final RegisterCandidateRequest registerCandidateRequest = buildCandidateRequest();
        mockMvc.perform(post("/api/v1/registerCandidate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(registerCandidateRequest)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public  void deleteCandidateTest() throws Exception {
        RegisterCandidateRequest registerCandidateRequest = buildCandidateRequest();
        mockMvc.perform(post("/api/v1/deleteCandidate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(registerCandidateRequest)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    private static RegisterCandidateRequest buildCandidateRequest() {
        String dateOfBirth = "2024-06-21";
        RegisterCandidateRequest registerCandidateRequest = new RegisterCandidateRequest();
        registerCandidateRequest.setName("Adewale");
        registerCandidateRequest.setPassword("password");
        registerCandidateRequest.setIdentificationNumber("401104");
        registerCandidateRequest.setAddress("123 Main St");
        registerCandidateRequest.setDateOfBirth(LocalDate.parse(dateOfBirth, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        registerCandidateRequest.setStateOfOrigin("Lagos");
        registerCandidateRequest.setElectionId(301L);
        registerCandidateRequest.setPositionContested(Category.NATIONAL);
        registerCandidateRequest.setPartyAffiliation("P.D.P");
        registerCandidateRequest.setVotingId(401104L);
        return registerCandidateRequest;
    }
}
