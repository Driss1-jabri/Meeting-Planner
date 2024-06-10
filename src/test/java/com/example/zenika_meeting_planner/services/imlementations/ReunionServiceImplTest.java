package com.example.zenika_meeting_planner.services.imlementations;


import com.example.zenika_meeting_planner.entities.Reunion;
import com.example.zenika_meeting_planner.entities.Reservation;
import com.example.zenika_meeting_planner.entities.Salle;
import com.example.zenika_meeting_planner.enums.Equipement;
import com.example.zenika_meeting_planner.enums.TypeReunion;
import com.example.zenika_meeting_planner.repositories.ReunionRepository;
import com.example.zenika_meeting_planner.repositories.ReservationRepository;
import com.example.zenika_meeting_planner.services.SalleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReunionServiceImplTest {

    @InjectMocks
    private ReunionServiceImpl reunionService;

    @Mock
    private ReunionRepository reunionRepository;

    @Mock
    private SalleService salleService;

    @Mock
    private ReservationRepository reservationRepository;

    private Reunion reunion;
    private Salle salle;

    @BeforeEach
    public void setUp() {
        reunion = new Reunion(1L, TypeReunion.VC, 10, LocalTime.of(9, 0), LocalTime.of(10, 0));
        salle = new Salle(1L, "Salle 1", 10, Arrays.asList(Equipement.ECRAN));
    }

    @Test
    public void testObtenirMeilleureSallePourReunion_SalleAvailable() {
        when(reunionRepository.findById(1L)).thenReturn(Optional.of(reunion));
        when(salleService.trouverMeilleureSalle(reunion)).thenReturn(Optional.of(salle));

        Optional<Salle> result = reunionService.obtenirMeilleureSallePourReunion(1L);

        assertTrue(result.isPresent());
        assertEquals(salle, result.get());
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    public void testObtenirMeilleureSallePourReunion_SalleNotAvailable() {
        when(reunionRepository.findById(1L)).thenReturn(Optional.of(reunion));
        when(salleService.trouverMeilleureSalle(reunion)).thenReturn(Optional.empty());

        Optional<Salle> result = reunionService.obtenirMeilleureSallePourReunion(1L);

        assertFalse(result.isPresent());
        verify(reservationRepository, never()).save(any(Reservation.class));
    }

    @Test
    public void testObtenirMeilleureSallePourReunion_ReunionNotFound() {
        when(reunionRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            reunionService.obtenirMeilleureSallePourReunion(1L);
        });

        String expectedMessage = "Réunion non trouvée";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testSaveReunion() {
        when(reunionRepository.save(reunion)).thenReturn(reunion);

        Reunion result = reunionService.saveReunion(reunion);

        assertNotNull(result);
        assertEquals(reunion.getId(), result.getId());
        verify(reunionRepository, times(1)).save(reunion);
    }

    @Test
    public void testDeleteReunion() {
        doNothing().when(reunionRepository).deleteById(1L);

        reunionService.deleteReunion(1L);

        verify(reunionRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testFindAllReunions() {
        List<Reunion> reunions = Arrays.asList(reunion);
        when(reunionRepository.findAll()).thenReturn(reunions);

        List<Reunion> result = reunionService.findAllReunions();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(reunionRepository, times(1)).findAll();
    }
}
