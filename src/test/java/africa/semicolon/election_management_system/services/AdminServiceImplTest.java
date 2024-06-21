package africa.semicolon.election_management_system.services;

import africa.semicolon.election_management_system.data.constants.Category;
import africa.semicolon.election_management_system.data.models.Admin;
import africa.semicolon.election_management_system.data.models.Candidate;
import africa.semicolon.election_management_system.data.repositories.AdminRepository;
import africa.semicolon.election_management_system.dtos.requests.*;
import africa.semicolon.election_management_system.dtos.responses.*;
import africa.semicolon.election_management_system.exceptions.ElectionManagementSystemBaseException;
import africa.semicolon.election_management_system.exceptions.UsernameExistsException;
import africa.semicolon.election_management_system.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.time.LocalDate;

import static africa.semicolon.election_management_system.data.constants.Category.NATIONAL;

import java.time.LocalDateTime;

import static java.time.Month.SEPTEMBER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = {"/db/data.sql"})
class AdminServiceImplTest {

    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminRepository adminRepository;

    @Test
    void testAdminCanRegisterSuccessfully() {
        RegisterAdminRequest adminRequest = new RegisterAdminRequest();
        adminRequest.setUsername("admin@test");
        adminRequest.setPassword("password");
        RegisterAdminResponse adminResponse = adminService.register(adminRequest);
        assertNotNull(adminResponse);
        assertTrue(adminResponse.getMessage().contains("successfully"));
    }

    @Test
    void testAdminCanNotRegisterWithAnEmptyUsername() {
        RegisterAdminRequest adminRequest = new RegisterAdminRequest();
        adminRequest.setUsername("");
        adminRequest.setPassword("");
        assertThat(adminRepository.count(), is(1L));
        try {
            adminService.register(adminRequest);
        } catch (ElectionManagementSystemBaseException message) {
            assertEquals("Username cannot be null or empty", message.getMessage());
        }
    }

    @Test
    @DisplayName("test that admin cannot be registered twice")
    public void registerAdminTest() {
        Long id = 200L;
        Admin admin = adminService.getAdminBy(id);
        assertThat(admin, notNullValue());
        RegisterAdminRequest request = new RegisterAdminRequest();
        request.setUsername("username");
        request.setPassword("password");
        assertThrows(UsernameExistsException.class, () -> adminService.register(request));
    }

    @Test
    public void scheduleElection_ElectionCanBeScheduledTest() {
        ScheduleElectionRequest request = new ScheduleElectionRequest();
        request. setTitle("LGA Election 3");
        request.setStartDate(LocalDateTime.of(2024, SEPTEMBER, 19, 12, 0));
        request.setEndDate(LocalDateTime.of(2024, SEPTEMBER, 21, 12, 0));
        request.setCategory(Category.LGA);
        ScheduleElectionResponse scheduleResponse = adminService.schedule(request);
        assertNotNull(scheduleResponse);
    }

    @Test
    public void registerCandidateTest() {
        RegisterCandidateRequest request = buildRequest();
        request.setElectionId(300L);
        RegisterCandidateResponse response = adminService.registerCandidate(request);
        assertNotNull(response);
        assertTrue(response.getMessage().contains("successfully"));
    }

    @Test
    @DisplayName("test that candidate cannot register outside a scheduled election")
    public void registerCandidateTest2() {
        RegisterCandidateRequest request = buildRequest();
        assertThrows(ResourceNotFoundException.class, ()-> adminService.registerCandidate(request));
        request.setElectionId(200L);
        assertThrows(ResourceNotFoundException.class, ()-> adminService.registerCandidate(request));
    }

    @Test
    public void deleteCandidateTest() {
        RegisterCandidateRequest request = buildRequest();
        request.setElectionId(300L);
        RegisterCandidateResponse response = adminService.registerCandidate(request);
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).contains("Candidate registered successfully");
        Candidate registeredCandidate = adminService.getCandidateBy(response.getId());
        assertThat(registeredCandidate.getName()).isEqualTo("John");

        DeleteCandidateResponse deleteCandidateResponse = adminService.deleteCandidate(registeredCandidate);

        assertThat(deleteCandidateResponse).isNotNull();
        assertThat(deleteCandidateResponse.getMessage()).isEqualTo("Candidate deleted successfully");

    }



    @Test
    public void updateCandidateTest(){
        RegisterCandidateRequest registerCandidateRequest = buildRequest();
        registerCandidateRequest.setElectionId(301L);
        buildRequest().setIdentificationNumber("4865389087");
        RegisterCandidateResponse registerCandidateResponse = adminService.registerCandidate(registerCandidateRequest);
        assertThat(registerCandidateResponse).isNotNull();
        assertThat(registerCandidateResponse.getMessage()).isEqualTo("Candidate registered successfully");
        Candidate registeredCandidate = adminService.getCandidateBy(registerCandidateResponse.getId());
        assertThat(registeredCandidate.getName()).isEqualTo("John");
        UpdateCandidateRequest updateCandidateRequest = new UpdateCandidateRequest();
        updateCandidateRequest.setName("Joseph");
        updateCandidateRequest.setIdentificationNumber(registeredCandidate.getIdentificationNumber());
        updateCandidateRequest.setElectionId(301L);
        registeredCandidate.setName(updateCandidateRequest.getName());
        UpdateCandidateResponse updateCandidateResponse = adminService.updateCandidate(updateCandidateRequest);
        assertThat(updateCandidateResponse).isNotNull();
        assertThat(updateCandidateResponse.getMessage()).isEqualTo("Candidate updated successfully");
        registeredCandidate = adminService.getCandidateBy(updateCandidateResponse.getId());
        assertThat(registeredCandidate.getName()).isEqualTo(updateCandidateRequest.getName());
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