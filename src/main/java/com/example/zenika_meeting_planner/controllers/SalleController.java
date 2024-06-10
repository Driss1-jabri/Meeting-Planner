package com.example.zenika_meeting_planner.controllers;

import com.example.zenika_meeting_planner.DTOs.SalleDTO;
import com.example.zenika_meeting_planner.entities.Salle;
import com.example.zenika_meeting_planner.services.SalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/salles")
public class SalleController {

    @Autowired
    private SalleService salleService;

    @PostMapping
    public ResponseEntity<SalleDTO> createSalle(@RequestBody SalleDTO salleDTO) {
        Salle salle = new Salle();
        salle.setNom(salleDTO.getNom());
        salle.setCapacite(salleDTO.getCapacite());
        salle.setEquipements(salleDTO.getEquipements());
        Salle createdSalle = salleService.saveSalle(salle);
        return ResponseEntity.ok(new SalleDTO(createdSalle.getId(), createdSalle.getNom(), createdSalle.getCapacite(), createdSalle.getEquipements()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSalle(@PathVariable Long id) {
        salleService.deleteSalle(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<SalleDTO>> getAllSalles() {
        List<Salle> salles = salleService.findAllSalles();
        List<SalleDTO> salleDTOs = salles.stream()
                .map(salle -> new SalleDTO(salle.getId(), salle.getNom(), salle.getCapacite(), salle.getEquipements()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(salleDTOs);
    }

    @GetMapping("/available")
    public ResponseEntity<List<Salle>> getAllAvailableSalles(
            @RequestParam("heureDebut") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime heureDebut,
            @RequestParam("heureFin") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime heureFin) {
        List<Salle> availableSalles = salleService.findAllAvailableSalles(heureDebut, heureFin);
        return ResponseEntity.ok(availableSalles);
    }

    @GetMapping("/{salleId}/disponibilite")
    public ResponseEntity<String> checkSalleDisponibilite(
            @PathVariable Long salleId,
            @RequestParam("heureDebut") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime heureDebut,
            @RequestParam("heureFin") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime heureFin) {

        Optional<Salle> salleOptional = salleService.findById(salleId);

        if (!salleOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Salle non trouvée");
        }

        Salle salle = salleOptional.get();
        boolean isAvailable = salleService.isAvailable(salle, heureDebut, heureFin);

        if (isAvailable) {
            return ResponseEntity.ok("La salle est disponible pour la plage horaire spécifiée.");
        } else {
            return ResponseEntity.ok("La salle n'est pas disponible pour la plage horaire spécifiée.");
        }
    }
}
