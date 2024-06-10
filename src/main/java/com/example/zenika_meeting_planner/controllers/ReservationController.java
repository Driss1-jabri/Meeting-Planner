package com.example.zenika_meeting_planner.controllers;

import com.example.zenika_meeting_planner.DTOs.ReservationDTO;
import com.example.zenika_meeting_planner.DTOs.ReunionDTO;
import com.example.zenika_meeting_planner.DTOs.SalleDTO;
import com.example.zenika_meeting_planner.entities.Reservation;
import com.example.zenika_meeting_planner.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public ResponseEntity<List<ReservationDTO>> getAllReservations() {
        List<Reservation> reservations = reservationService.findAllReservations();
        List<ReservationDTO> reservationDTOs = reservations.stream()
                .map(reservation -> new ReservationDTO(
                        reservation.getId(),
                        new SalleDTO(reservation.getSalle().getId(), reservation.getSalle().getNom(), reservation.getSalle().getCapacite(), reservation.getSalle().getEquipements()),
                        new ReunionDTO(reservation.getReunion().getId(), reservation.getReunion().getType(), reservation.getReunion().getNombrePersonnes(), reservation.getReunion().getHeureDebut(), reservation.getReunion().getHeureFin())
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(reservationDTOs);
    }
}
