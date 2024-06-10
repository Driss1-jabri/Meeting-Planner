package com.example.zenika_meeting_planner.controllers;

import com.example.zenika_meeting_planner.DTOs.ReunionDTO;
import com.example.zenika_meeting_planner.DTOs.ReservationDTO;
import com.example.zenika_meeting_planner.DTOs.SalleDTO;
import com.example.zenika_meeting_planner.entities.Reunion;
import com.example.zenika_meeting_planner.entities.Reservation;
import com.example.zenika_meeting_planner.entities.Salle;
import com.example.zenika_meeting_planner.repositories.ReservationRepository;
import com.example.zenika_meeting_planner.services.ReunionService;
import com.example.zenika_meeting_planner.services.SalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reunions")
public class ReunionController {

    @Autowired
    private ReunionService reunionService;

    @Autowired
    private SalleService salleService;

    @Autowired
    private ReservationRepository reservationRepository;


    @GetMapping("/{reunionId}/meilleure-salle")
    public ResponseEntity<?> obtenirMeilleureSalle(@PathVariable Long reunionId) {
        Optional<Salle> meilleureSalle = reunionService.obtenirMeilleureSallePourReunion(reunionId);
        if (meilleureSalle.isPresent()) {
            return ResponseEntity.ok(meilleureSalle.get());
        } else {
            return ResponseEntity.status(404).body("Aucune salle disponible pour cette réunion");
        }
    }

    @PostMapping
    public ResponseEntity<?> createReunion(@RequestBody ReunionDTO reunionDTO) {
        Reunion reunion = new Reunion();
        reunion.setType(reunionDTO.getType());
        reunion.setNombrePersonnes(reunionDTO.getNombrePersonnes());
        reunion.setHeureDebut(reunionDTO.getHeureDebut());
        reunion.setHeureFin(reunionDTO.getHeureFin());
        Reunion createdReunion = reunionService.saveReunion(reunion);

        Optional<Salle> salleOptional = salleService.trouverMeilleureSalle(createdReunion);
        if (salleOptional.isPresent()) {
            Salle salle = salleOptional.get();
            Reservation reservation = new Reservation();
            reservation.setReunion(createdReunion);
            reservation.setSalle(salle);
            Reservation createdReservation = reservationRepository.save(reservation);
            return ResponseEntity.ok(new ReservationDTO(createdReservation.getId(), new SalleDTO(salle.getId(), salle.getNom(), salle.getCapacite(), salle.getEquipements()), reunionDTO));
        } else {
            return ResponseEntity.status(404).body("Aucune salle disponible pour cette réunion");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReunion(@PathVariable Long id) {
        reunionService.deleteReunion(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ReunionDTO>> getAllReunions() {
        List<Reunion> reunions = reunionService.findAllReunions();
        List<ReunionDTO> reunionDTOs = reunions.stream()
                .map(reunion -> new ReunionDTO(reunion.getId(), reunion.getType(), reunion.getNombrePersonnes(), reunion.getHeureDebut(), reunion.getHeureFin()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(reunionDTOs);
    }
}
