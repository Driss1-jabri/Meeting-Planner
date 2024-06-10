package com.example.zenika_meeting_planner.controllers;

import com.example.zenika_meeting_planner.DTOs.ReservationDTO;
import com.example.zenika_meeting_planner.entities.Reservation;
import com.example.zenika_meeting_planner.entities.Reunion;
import com.example.zenika_meeting_planner.entities.Salle;
import com.example.zenika_meeting_planner.enums.Equipement;
import com.example.zenika_meeting_planner.enums.TypeReunion;
import com.example.zenika_meeting_planner.services.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ReservationControllerTest {

    @Mock
    private ReservationService reservationService;
    @InjectMocks
    private ReservationController reservationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDeleteReservation() {
        Long reservationId = 1L;
        doNothing().when(reservationService).deleteReservation(reservationId);
        ResponseEntity<Void> response = reservationController.deleteReservation(reservationId);
        assertEquals(ResponseEntity.noContent().build(), response);
        verify(reservationService, times(1)).deleteReservation(reservationId);
    }

    @Test
    void testGetAllReservations() {
        Salle salle = new Salle(1L, "Salle A", 10, Arrays.asList(Equipement.ECRAN, Equipement.TABLEAU));
        Reunion reunion = new Reunion(1L, TypeReunion.VC, 10, LocalTime.of(9, 0), LocalTime.of(10, 0));
        Reservation reservation = new Reservation(1L, salle, reunion);
        when(reservationService.findAllReservations()).thenReturn(Collections.singletonList(reservation));
        ResponseEntity<List<ReservationDTO>> response = reservationController.getAllReservations();
        assertEquals(1, response.getBody().size());
        ReservationDTO reservationDTO = response.getBody().get(0);
        assertEquals(reservation.getId(), reservationDTO.getId());
        assertEquals(reservation.getSalle().getId(), reservationDTO.getSalle().getId());
        assertEquals(reservation.getReunion().getId(), reservationDTO.getReunion().getId());
        verify(reservationService, times(1)).findAllReservations();
    }
}
