package com.example.zenika_meeting_planner.services.imlementations;





import com.example.zenika_meeting_planner.entities.Reservation;
import com.example.zenika_meeting_planner.entities.Reunion;
import com.example.zenika_meeting_planner.entities.Salle;
import com.example.zenika_meeting_planner.enums.Equipement;
import com.example.zenika_meeting_planner.enums.TypeReunion;
import com.example.zenika_meeting_planner.repositories.ReservationRepository;
import com.example.zenika_meeting_planner.repositories.SalleRepository;
import com.example.zenika_meeting_planner.services.SalleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SalleServiceImpl implements SalleService {

    @Autowired
    private SalleRepository salleRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public Optional<Salle> trouverMeilleureSalle(Reunion reunion) {
        List<Salle> salles = salleRepository.findAll();

        salles = filterByCapacity(salles, reunion.getNombrePersonnes());

        salles = filterByEquipments(salles, reunion.getType());

        salles = filterByAvailability(salles, reunion);

        salles = sortBySuitability(salles, reunion);
        return salles.isEmpty() ? Optional.empty() : Optional.of(salles.get(0));
    }
    @Override
    public Salle saveSalle(Salle salle) {
        return salleRepository.save(salle);
    }

    @Override
    public void deleteSalle(Long id) {
        salleRepository.deleteById(id);
    }

    public List<Salle> filterByCapacity(List<Salle> salles, int nombrePersonnes) {

        int capaciteMinimale = (int) Math.ceil(nombrePersonnes / 0.7);
        return salles.stream()
                .filter(salle -> salle.getCapacite() >= capaciteMinimale)
                .collect(Collectors.toList());
    }

    public List<Salle> filterByEquipments(List<Salle> salles, TypeReunion typeReunion) {
        return salles.stream()
                .filter(salle -> {
                    List<Equipement> equipements = salle.getEquipements();
                    switch (typeReunion) {
                        case VC:
                            return equipements.contains(Equipement.ECRAN) &&
                                    equipements.contains(Equipement.WEBCAM) &&
                                    equipements.contains(Equipement.PIEUVRE);
                        case SPEC:
                            return equipements.contains(Equipement.TABLEAU);
                        case RS:
                            return true;
                        case RC:
                            return equipements.contains(Equipement.TABLEAU) &&
                                    equipements.contains(Equipement.ECRAN) &&
                                    equipements.contains(Equipement.PIEUVRE);
                        default:
                            return false;
                    }
                })
                .collect(Collectors.toList());
    }

    public List<Salle> filterByAvailability(List<Salle> salles, Reunion reunion) {
        return salles.stream()
                .filter(salle -> {
                    List<Reservation> reservations = reservationRepository.findBySalle(salle);
                    LocalTime heureDebut = reunion.getHeureDebut();
                    LocalTime heureFin = reunion.getHeureFin();
                    for (Reservation reservation : reservations) {
                        LocalTime reservationDebut = reservation.getReunion().getHeureDebut();
                        LocalTime reservationFin = reservation.getReunion().getHeureFin();
                        if ((heureDebut.isBefore(reservationFin.plusHours(1)) && heureFin.isAfter(reservationDebut.minusHours(1)))) {
                            return false;
                        }
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }

    public List<Salle> sortBySuitability(List<Salle> salles, Reunion reunion) {
        return salles.stream()
                .sorted((salle1, salle2) -> {

                    int capaciteCompare = Integer.compare(salle1.getCapacite(), salle2.getCapacite());
                    if (capaciteCompare != 0) return capaciteCompare;


                    int equipementsCompare = Integer.compare(salle1.getEquipements().size(), salle2.getEquipements().size());
                    return equipementsCompare;
                })
                .collect(Collectors.toList());
    }
    @Override
    public List<Salle> findAllSalles() {
        return salleRepository.findAll();
    }

    @Override
    public List<Salle> findAllAvailableSalles(LocalTime heureDebut, LocalTime heureFin) {
        return salleRepository.findAll().stream()
                .filter(salle -> isAvailable(salle, heureDebut, heureFin))
                .collect(Collectors.toList());
    }
    @Override
    public boolean isAvailable(Salle salle, LocalTime heureDebut, LocalTime heureFin) {
        List<Reservation> reservations = reservationRepository.findBySalle(salle);
        for (Reservation reservation : reservations) {
            LocalTime reservationDebut = reservation.getReunion().getHeureDebut();
            LocalTime reservationFin = reservation.getReunion().getHeureFin();
            if ((heureDebut.isBefore(reservationFin.plusHours(1)) && heureFin.isAfter(reservationDebut.minusHours(1)))) {
                return false;
            }
        }
        return true;
    }
    @Override
    public Optional<Salle> findById(Long salleId) {
        return salleRepository.findById(salleId);
    }
}
