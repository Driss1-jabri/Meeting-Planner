package com.example.zenika_meeting_planner.services.imlementations;


import com.example.zenika_meeting_planner.entities.Reservation;
import com.example.zenika_meeting_planner.entities.Reunion;
import com.example.zenika_meeting_planner.entities.Salle;
import com.example.zenika_meeting_planner.repositories.ReservationRepository;
import com.example.zenika_meeting_planner.repositories.ReunionRepository;

import com.example.zenika_meeting_planner.services.ReunionService;
import com.example.zenika_meeting_planner.services.SalleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReunionServiceImpl implements ReunionService {

    @Autowired
    private ReunionRepository reunionRepository;

    @Autowired
    private SalleService salleService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public Optional<Salle> obtenirMeilleureSallePourReunion(Long reunionId) {
        Reunion reunion = reunionRepository.findById(reunionId)
                .orElseThrow(() -> new RuntimeException("Réunion non trouvée"));
        Optional<Salle> meilleureSalle = salleService.trouverMeilleureSalle(reunion);
        meilleureSalle.ifPresent(salle -> {
            Reservation reservation = new Reservation();
            reservation.setReunion(reunion);
            reservation.setSalle(salle);
            reservationRepository.save(reservation);
        });
        return meilleureSalle;
    }
    @Override
    public Reunion saveReunion(Reunion reunion) {
        return reunionRepository.save(reunion);
    }

    @Override
    public void deleteReunion(Long id) {
        reunionRepository.deleteById(id);
    }
    @Override
    public List<Reunion> findAllReunions() {
        return reunionRepository.findAll();
    }
}
