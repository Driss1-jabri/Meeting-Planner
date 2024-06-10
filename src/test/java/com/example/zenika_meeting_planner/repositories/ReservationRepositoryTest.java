package com.example.zenika_meeting_planner.repositories;

import com.example.zenika_meeting_planner.entities.Reservation;
import com.example.zenika_meeting_planner.entities.Reunion;
import com.example.zenika_meeting_planner.entities.Salle;
import com.example.zenika_meeting_planner.enums.Equipement;
import com.example.zenika_meeting_planner.enums.TypeReunion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private SalleRepository salleRepository;

    @Autowired
    private ReunionRepository reunionRepository;

    private Salle salle;
    private Reunion reunion;

    @BeforeEach
    public void setUp() {
        salle = new Salle(null, "E1001", 10, Arrays.asList(Equipement.ECRAN));
        salleRepository.save(salle);

        reunion = new Reunion(null, TypeReunion.VC, 5, LocalTime.of(9, 0), LocalTime.of(10, 0));
        reunionRepository.save(reunion);
    }

    @Test
    public void testFindBySalle() {
        Reservation reservation = new Reservation();
        reservation.setSalle(salle);
        reservation.setReunion(reunion);
        reservationRepository.save(reservation);

        List<Reservation> foundReservations = reservationRepository.findBySalle(salle);

        assertThat(foundReservations).isNotEmpty();
        assertThat(foundReservations.get(0).getSalle().getNom()).isEqualTo("E1001");
    }
}
