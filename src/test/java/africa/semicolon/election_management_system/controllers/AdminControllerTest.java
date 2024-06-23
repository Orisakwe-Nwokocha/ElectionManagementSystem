package africa.semicolon.election_management_system.controllers;

import africa.semicolon.election_management_system.data.constants.Category;
import africa.semicolon.election_management_system.dtos.requests.RegisterAdminRequest;
import africa.semicolon.election_management_system.dtos.requests.ScheduleElectionRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static java.time.Month.SEPTEMBER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts ={"/db/data.sql"})
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void testRegisterAdmin() throws Exception {
        RegisterAdminRequest registerAdminRequest = new RegisterAdminRequest();
        registerAdminRequest.setUsername("Testing@One");
        registerAdminRequest.setPassword("password");
        mockMvc.perform(post("/api/v1/admin/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(registerAdminRequest)))
                .andExpect(status().isCreated())
                .andDo(print());
    }


    @Test
    void testSchedulingElectionEndPoint() throws Exception {
        ScheduleElectionRequest scheduleElectionRequest = new ScheduleElectionRequest();
        scheduleElectionRequest.setTitle("LGA Election 3");
        scheduleElectionRequest.setCategory(Category.LGA);
        scheduleElectionRequest.setStartDate(LocalDateTime.of(2024, SEPTEMBER, 19, 12, 0));
        scheduleElectionRequest.setEndDate(LocalDateTime.of(2024, SEPTEMBER, 21, 12, 0));
        mockMvc.perform(post("/api/v1/admin/schedule-election")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(scheduleElectionRequest)))
                .andExpect(status().isCreated())
                .andDo(print());
    }
}