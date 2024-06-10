package com.example.zenika_meeting_planner.repositories;
import com.example.zenika_meeting_planner.entities.Reservation;
import com.example.zenika_meeting_planner.entities.Salle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findBySalle(Salle salle);
}
