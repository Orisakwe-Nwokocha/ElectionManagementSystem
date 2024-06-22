package africa.semicolon.election_management_system.controllers;


import africa.semicolon.election_management_system.dtos.requests.RegisterAdminRequest;
import africa.semicolon.election_management_system.dtos.requests.ScheduleElectionRequest;
import africa.semicolon.election_management_system.dtos.requests.UpdateCandidateRequest;
import africa.semicolon.election_management_system.dtos.requests.UpdateVoterRequest;
import africa.semicolon.election_management_system.services.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final AdminService adminService;


    @PostMapping
    public ResponseEntity<?> registerAdmin(@ModelAttribute RegisterAdminRequest registerAdminRequest) throws IOException {
        return ResponseEntity.status(CREATED)
                .body(adminService.register(registerAdminRequest));
    }

    @PostMapping("/scheduleElection")
    public ResponseEntity<?> scheduleElection(@ModelAttribute ScheduleElectionRequest scheduleElectionRequest) throws IOException {
        return ResponseEntity.status(CREATED)
                .body(adminService.schedule(scheduleElectionRequest));
    }
}
