package africa.semicolon.election_management_system.controllers;


import africa.semicolon.election_management_system.services.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/admin")
public class adminController {

    private final AdminService adminService;

}
