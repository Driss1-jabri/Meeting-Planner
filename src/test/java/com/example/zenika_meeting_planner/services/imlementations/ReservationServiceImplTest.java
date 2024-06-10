package com.example.zenika_meeting_planner.services.imlementations;



import com.example.zenika_meeting_planner.entities.Reservation;
import com.example.zenika_meeting_planner.entities.Reunion;
import com.example.zenika_meeting_planner.entities.Salle;
import com.example.zenika_meeting_planner.repositories.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @Test
    public void testFindAllReservations() {
        Reservation reservation1 = new Reservation(1L, new Salle(), new Reunion());
        Reservation reservation2 = new Reservation(2L, new Salle(), new Reunion());

        when(reservationRepository.findAll()).thenReturn(Arrays.asList(reservation1, reservation2));

        List<Reservation> reservations = reservationService.findAllReservations();

        assertEquals(2, reservations.size());
        verify(reservationRepository, times(1)).findAll();
    }



    @Test
    public void testDeleteReservation() {
        doNothing().when(reservationRepository).deleteById(1L);

        reservationService.deleteReservation(1L);

        verify(reservationRepository, times(1)).deleteById(1L);
    }
}
