package africa.semicolon.election_management_system.controllers;

import africa.semicolon.election_management_system.dtos.requests.RegisterAdminRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
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

        mockMvc.perform(post("/api/v1/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(registerAdminRequest)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

}