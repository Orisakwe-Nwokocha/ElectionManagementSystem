package africa.semicolon.election_management_system.controllers;

import africa.semicolon.election_management_system.dtos.requests.CastVoteRequest;
import africa.semicolon.election_management_system.dtos.responses.UpdateVoterResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Sql(scripts = {"/db/data.sql"})
public class VoterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCastVote() throws Exception {
        CastVoteRequest request = new CastVoteRequest();
        request.setVotingId(654321L);
        request.setElectionId(301L);
        request.setCandidateId(400L);
        mockMvc.perform(post("/api/v1/voter")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    public void testUpdateVotersDetail() throws Exception {
        Long votingId = 654321L;
        String jsonPatch = "[{\"op\":\"replace\",\"path\":\"/address\",\"value\":\"4 Afolabi street\"}]";
        UpdateVoterResponse response = new UpdateVoterResponse();
        response.setAddress("");
        mockMvc.perform(patch("/api/v1/voter/update/{votingId}", votingId)
                        .contentType("application/json-patch+json")
                        .content(jsonPatch))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.address").value("4 Afolabi street"))
                .andDo(print());


    }
}