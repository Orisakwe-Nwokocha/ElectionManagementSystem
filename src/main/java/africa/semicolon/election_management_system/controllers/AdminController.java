package africa.semicolon.election_management_system.controllers;


import africa.semicolon.election_management_system.dtos.requests.RegisterAdminRequest;
import africa.semicolon.election_management_system.dtos.requests.ScheduleElectionRequest;
import africa.semicolon.election_management_system.dtos.responses.UpdateVoterResponse;
import africa.semicolon.election_management_system.services.AdminService;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final AdminService adminService;


    @PostMapping("/register")
    public ResponseEntity<?> registerAdmin(@RequestBody RegisterAdminRequest registerAdminRequest) {
        return ResponseEntity.status(CREATED)
                .body(adminService.register(registerAdminRequest));
    }

    @PostMapping("/schedule-election")
    public ResponseEntity<?> scheduleElection(@RequestBody ScheduleElectionRequest scheduleElectionRequest) {
        return ResponseEntity.status(CREATED)
                .body(adminService.schedule(scheduleElectionRequest));
    }

    @PatchMapping("/update-as-admin/{votingId}")
    public ResponseEntity<?> updateVotersDetail(@PathVariable Long votingId, @RequestBody JsonPatch jsonPatch) {
        UpdateVoterResponse response = adminService.updateVoterAsAdmin(votingId, jsonPatch);
        return ResponseEntity.ok(response);

    }
}
