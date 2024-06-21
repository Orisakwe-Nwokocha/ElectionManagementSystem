package africa.semicolon.election_management_system.controllers;

import africa.semicolon.election_management_system.services.VoterService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/voter")
@AllArgsConstructor
public class VoterController {
    private final VoterService voterService;


}
