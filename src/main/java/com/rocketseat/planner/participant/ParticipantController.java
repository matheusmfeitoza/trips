package com.rocketseat.planner.participant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/participants")
public class ParticipantController {
    @Autowired
    private ParticipantRepository repository;

    @PostMapping("/{id}/confirm")
    public ResponseEntity<Participant> confirmParticipant(@PathVariable("id") UUID id, @RequestBody ParticipantRequestPayload payload) {
        Optional<Participant> optionalParticipant = repository.findById(id);

        if(optionalParticipant.isEmpty()) return ResponseEntity.notFound().build();

        Participant participant = optionalParticipant.get();
        participant.setConfirmed(true);
        participant.setName(payload.name());
        this.repository.save(participant);

        return ResponseEntity.ok(participant);
    }
}
