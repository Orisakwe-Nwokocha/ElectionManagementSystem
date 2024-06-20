package africa.semicolon.election_management_system.services;

import africa.semicolon.election_management_system.data.models.Candidate;
import africa.semicolon.election_management_system.data.repositories.CandidateRepository;
import africa.semicolon.election_management_system.dtos.requests.RegisterCandidateRequest;
import africa.semicolon.election_management_system.dtos.response.RegisterCandidateResponse;
import africa.semicolon.election_management_system.exceptions.CandidateNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CandidateServiceImplementation implements CandidateService{
    @Autowired
   private CandidateRepository candidateRepository;

   private  final ModelMapper modelMapper;

    public CandidateServiceImplementation(ModelMapper modelMapper){
        this.modelMapper = modelMapper;

    }

   @Override
    public RegisterCandidateResponse registerCandidate(RegisterCandidateRequest request){
        Candidate candidate =  modelMapper.map(request, Candidate.class);
        candidateRepository.save(candidate);
        var response = modelMapper.map(candidate, RegisterCandidateResponse.class);
        response.setMessage("Registration successful");
        return response;
    }

    public Candidate findCandidateBy(Long candidate){
        return candidateRepository.findById(candidate)
                .orElseThrow(()-> new CandidateNotFoundException("Candidate not found"));
    }
}
