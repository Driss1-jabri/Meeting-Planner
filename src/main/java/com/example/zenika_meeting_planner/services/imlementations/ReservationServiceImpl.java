package com.example.zenika_meeting_planner.services.imlementations;
import com.example.zenika_meeting_planner.entities.Reservation;
import com.example.zenika_meeting_planner.repositories.ReservationRepository;
import com.example.zenika_meeting_planner.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
    @Override
    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }
}