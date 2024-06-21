package africa.semicolon.election_management_system.controllers;

import africa.semicolon.election_management_system.services.VoterService;
import africa.semicolon.election_management_system.dtos.requests.CreateVoterRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("api/v1/voter")
@AllArgsConstructor
public class VoterController {
    private final VoterService voterService;

    @PostMapping
    public ResponseEntity<?> registerVoter(@ModelAttribute CreateVoterRequest createVoterRequest) throws Exception{
        return ResponseEntity.status(CREATED)
                .body(voterService.registerVoter(createVoterRequest));
    }


}
