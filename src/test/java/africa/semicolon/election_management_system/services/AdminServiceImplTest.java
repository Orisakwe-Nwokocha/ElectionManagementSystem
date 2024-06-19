package africa.semicolon.election_management_system.services;

import africa.semicolon.election_management_system.data.repositories.AdminRepository;
import africa.semicolon.election_management_system.dtos.requests.RegisterAdminRequest;
import africa.semicolon.election_management_system.dtos.responses.RegisterAdminResponse;
import africa.semicolon.election_management_system.exceptions.ElectionManagementSystemBaseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
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
        RegisterAdminResponse adminResponse =  adminService.register(adminRequest);;
        assertNotNull (adminResponse);
        assertTrue(adminResponse.getMessage().contains("successfully"));
    }

    @Test
    void testAdminCanNotRegisterWithAnEmptyUsername(){
        RegisterAdminRequest adminRequest = new RegisterAdminRequest();
        adminRequest.setUsername("");
        adminRequest.setPassword("");
        assertThat(adminRepository.count(), is(1L));
        try{
            adminService.register(adminRequest);

        } catch (ElectionManagementSystemBaseException message){
            assertEquals("Username cannot be null or empty", message.getMessage());

        }
    }
    @Test
    void schedule() {
    }
}