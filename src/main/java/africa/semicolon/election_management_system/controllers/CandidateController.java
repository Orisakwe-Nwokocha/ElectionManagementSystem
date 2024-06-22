package africa.semicolon.election_management_system.controllers;

import africa.semicolon.election_management_system.dtos.requests.DeleteCandidateRequest;
import africa.semicolon.election_management_system.dtos.requests.RegisterCandidateRequest;
import africa.semicolon.election_management_system.dtos.requests.UpdateCandidateRequest;
import africa.semicolon.election_management_system.dtos.responses.DeleteCandidateResponse;
import africa.semicolon.election_management_system.dtos.responses.RegisterCandidateResponse;
import africa.semicolon.election_management_system.dtos.responses.UpdateCandidateResponse;
import africa.semicolon.election_management_system.services.CandidateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RequestMapping("/ap1/v1/candidate")
@RestController
public class CandidateController {
    private final CandidateService candidaService;

    public CandidateController(CandidateService candidaService) {
        this.candidaService = candidaService;
    }

    @PostMapping("/registerCandidate")
    public ResponseEntity<RegisterCandidateResponse> registerCandidate(@ModelAttribute RegisterCandidateRequest registerCandidateRequest){
        return ResponseEntity.status(CREATED)
                .body(candidaService.registerCandidate(registerCandidateRequest));
    }

    @PostMapping("/deleteCandidate")
    public ResponseEntity<DeleteCandidateResponse> deleteCandidate(@ModelAttribute DeleteCandidateRequest deleteCandidateRequest){
        return ResponseEntity.status(HttpStatus.OK)
               .body(candidaService.deleteCandidate(deleteCandidateRequest));
    }

    @PostMapping("/updateCandidate")
    public ResponseEntity<UpdateCandidateResponse> updateCandidate(@ModelAttribute UpdateCandidateRequest updateCandidateRequest){
        return ResponseEntity.status(HttpStatus.OK)
               .body(candidaService.updateCandidate(updateCandidateRequest));
    }
}
