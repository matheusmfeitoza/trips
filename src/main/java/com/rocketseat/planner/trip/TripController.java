package com.rocketseat.planner.trip;

import com.rocketseat.planner.participant.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.rocketseat.planner.trip.ResponseRecords.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/trips")
public class TripController {

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private TripRepository repository;

    @PostMapping
    public ResponseEntity<CustomReturn<TripUuid>> createTrip(@RequestBody TripRequestPayload payload) {
        Trip newTrip = new Trip(payload);

        this.repository.save(newTrip);
        this.participantService.registerParticipantsToEvent(payload.emails_to_invite(), newTrip.getId());

        return ResponseEntity.ok(new CustomReturn<>(new TripUuid(newTrip.getId()),"Trip salva com sucesso!!"));

    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomReturn<Trip>> getTripDetails(@PathVariable UUID id) {
        Optional<Trip> trip = this.repository.findById(id);

        return trip.map(value -> ResponseEntity.ok(new CustomReturn<>(value, "Retornei a trip que voce solicitou! ;)")))
                .orElseGet(() -> ResponseEntity.status(404).body(new CustomReturn<>(null, "Ops!!! Nao achei a trip, confere se ta tudo certo ai!?")));
    }

    @GetMapping
    public ResponseEntity<CustomReturn> getTrips() {
        List<Trip> trip = this.repository.findAll();

        return ResponseEntity.ok(new CustomReturn<>(trip, "Retornamos todas as Trips para voce!"));
    }

}
