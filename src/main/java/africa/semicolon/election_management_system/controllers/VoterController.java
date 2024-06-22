package africa.semicolon.election_management_system.controllers;

import africa.semicolon.election_management_system.dtos.requests.CastVoteRequest;
import africa.semicolon.election_management_system.dtos.requests.UpdateVoterRequest;
import africa.semicolon.election_management_system.dtos.responses.UpdateVoterResponse;
import africa.semicolon.election_management_system.services.VoterService;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/voter")
@AllArgsConstructor
public class VoterController {
    private final VoterService voterService;

    @PostMapping
    public ResponseEntity<?> castVote(@ModelAttribute CastVoteRequest request){
        return ResponseEntity.ok(voterService.castVote(request));
    }

    @PatchMapping
    public ResponseEntity<?> updateVotersDetail(@PathVariable Long votingId, @RequestBody JsonPatch jsonPatch) {
        UpdateVoterResponse response = voterService.updateVoter(votingId, jsonPatch);
        return ResponseEntity.ok(response);

    }





}
