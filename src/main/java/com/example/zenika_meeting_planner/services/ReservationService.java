package com.example.zenika_meeting_planner.services;

import com.example.zenika_meeting_planner.entities.Reservation;
import com.example.zenika_meeting_planner.entities.Reunion;

import java.util.List;

public interface ReservationService {
    public void deleteReservation(Long id);
    public List<Reservation> findAllReservations();
}