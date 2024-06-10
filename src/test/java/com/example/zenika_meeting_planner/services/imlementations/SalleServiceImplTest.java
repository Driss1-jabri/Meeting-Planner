package com.example.zenika_meeting_planner.services.imlementations;



import com.example.zenika_meeting_planner.entities.Salle;
import com.example.zenika_meeting_planner.repositories.SalleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SalleServiceImplTest {

    @Mock
    private SalleRepository salleRepository;

    @InjectMocks
    private SalleServiceImpl salleService;

    @Test
    public void testFindAllSalles() {
        Salle salle1 = new Salle(1L, "E1001", 23, Arrays.asList());
        Salle salle2 = new Salle(2L, "E1002", 10, Arrays.asList());

        when(salleRepository.findAll()).thenReturn(Arrays.asList(salle1, salle2));

        List<Salle> salles = salleService.findAllSalles();

        assertEquals(2, salles.size());
        verify(salleRepository, times(1)).findAll();
    }

    @Test
    public void testFindByIdSuccess() {
        Salle salle = new Salle(1L, "E1001", 23, Arrays.asList());

        when(salleRepository.findById(1L)).thenReturn(Optional.of(salle));

        Optional<Salle> foundSalle = salleService.findById(1L);

        assertTrue(foundSalle.isPresent());
        assertEquals("E1001", foundSalle.get().getNom());
        verify(salleRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindByIdNotFound() {
        when(salleRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Salle> foundSalle = salleService.findById(1L);

        assertFalse(foundSalle.isPresent());
        verify(salleRepository, times(1)).findById(1L);
    }

    @Test
    public void testSaveSalle() {
        Salle salle = new Salle(1L, "E1001", 23, Arrays.asList());

        when(salleRepository.save(salle)).thenReturn(salle);

        Salle savedSalle = salleService.saveSalle(salle);

        assertEquals("E1001", savedSalle.getNom());
        verify(salleRepository, times(1)).save(salle);
    }

    @Test
    public void testDeleteSalle() {
        doNothing().when(salleRepository).deleteById(1L);

        salleService.deleteSalle(1L);

        verify(salleRepository, times(1)).deleteById(1L);
    }
}
