package africa.semicolon.election_management_system.controllers;

import africa.semicolon.election_management_system.dtos.requests.DeleteCandidateRequest;
import africa.semicolon.election_management_system.dtos.requests.RegisterCandidateRequest;
import africa.semicolon.election_management_system.dtos.requests.UpdateCandidateRequest;
import africa.semicolon.election_management_system.dtos.responses.DeleteCandidateResponse;
import africa.semicolon.election_management_system.dtos.responses.RegisterCandidateResponse;
import africa.semicolon.election_management_system.dtos.responses.UpdateCandidateResponse;
import africa.semicolon.election_management_system.services.CandidateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/candidate")
public class CandidateController {
    private final CandidateService candidaService;

    public CandidateController(CandidateService candidaService) {
        this.candidaService = candidaService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterCandidateResponse> registerCandidate(@ModelAttribute RegisterCandidateRequest registerCandidateRequest){
        return ResponseEntity.status(CREATED)
                .body(candidaService.registerCandidate(registerCandidateRequest));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<DeleteCandidateResponse> deleteCandidate(@ModelAttribute DeleteCandidateRequest deleteCandidateRequest){
        return ResponseEntity.ok()
                .body(candidaService.deleteCandidate(deleteCandidateRequest));
    }

    @PatchMapping("/update")
    public ResponseEntity<UpdateCandidateResponse> updateCandidate(@ModelAttribute UpdateCandidateRequest updateCandidateRequest){
        return ResponseEntity.ok()
                .body(candidaService.updateCandidate(updateCandidateRequest));
    }
}
