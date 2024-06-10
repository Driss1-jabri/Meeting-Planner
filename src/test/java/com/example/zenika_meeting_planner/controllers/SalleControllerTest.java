package com.example.zenika_meeting_planner.controllers;



import com.example.zenika_meeting_planner.DTOs.SalleDTO;
import com.example.zenika_meeting_planner.entities.Salle;
import com.example.zenika_meeting_planner.services.SalleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class SalleControllerTest {

    @Mock
    private SalleService salleService;

    @InjectMocks
    private SalleController salleController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(salleController).build();
    }

    @Test
    public void testGetAllSalles() throws Exception {
        Salle salle1 = new Salle(1L, "E1001", 23, Arrays.asList());
        Salle salle2 = new Salle(2L, "E1002", 10, Arrays.asList());
        List<SalleDTO> salleDTOs = Arrays.asList(new SalleDTO(salle1), new SalleDTO(salle2));

        when(salleService.findAllSalles()).thenReturn(Arrays.asList(salle1, salle2));

        mockMvc.perform(get("/api/salles")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nom").value("E1001"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].nom").value("E1002"));
        verify(salleService, times(1)).findAllSalles();
    }

    @Test
    public void testGetAllAvailableSalles() throws Exception {
        Salle salle1 = new Salle(1L, "E1001", 23, Arrays.asList());
        Salle salle2 = new Salle(2L, "E1002", 10, Arrays.asList());
        List<Salle> availableSalles = Arrays.asList(salle1, salle2);
        when(salleService.findAllAvailableSalles(any(LocalTime.class), any(LocalTime.class)))
                .thenReturn(availableSalles);

        mockMvc.perform(get("/api/salles/available")
                        .param("heureDebut", "08:00:00")
                        .param("heureFin", "10:00:00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nom").value("E1001"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].nom").value("E1002"));

        verify(salleService, times(1)).findAllAvailableSalles(any(LocalTime.class), any(LocalTime.class));
    }

    @Test
    public void testCheckSalleDisponibiliteSuccess() throws Exception {
        Salle salle = new Salle(1L, "E1001", 23, Arrays.asList());

        when(salleService.findById(1L)).thenReturn(Optional.of(salle));
        when(salleService.isAvailable(any(Salle.class), any(LocalTime.class), any(LocalTime.class)))
                .thenReturn(true);

        mockMvc.perform(get("/api/salles/1/disponibilite")
                        .param("heureDebut", "08:00:00")
                        .param("heureFin", "10:00:00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("La salle est disponible pour la plage horaire spécifiée."));
        verify(salleService, times(1)).findById(1L);
        verify(salleService, times(1)).isAvailable(any(Salle.class), any(LocalTime.class), any(LocalTime.class));
    }

    @Test
    public void testCheckSalleDisponibiliteNotFound() throws Exception {
        when(salleService.findById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/salles/1/disponibilite")
                        .param("heureDebut", "08:00:00")
                        .param("heureFin", "10:00:00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Salle non trouvée"));

        verify(salleService, times(1)).findById(1L);
        verify(salleService, never()).isAvailable(any(Salle.class), any(LocalTime.class), any(LocalTime.class));
    }

    @Test
    public void testCreateSalle() throws Exception {
        SalleDTO salleDTO = new SalleDTO(1L, "E1001", 23, Arrays.asList());
        Salle salle = new Salle(1L, "E1001", 23, Arrays.asList());
        when(salleService.saveSalle(any(Salle.class))).thenReturn(salle);
        mockMvc.perform(post("/api/salles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nom\": \"E1001\", \"capacite\": 23, \"equipements\": []}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nom").value("E1001"))
                .andExpect(jsonPath("$.capacite").value(23));
        verify(salleService, times(1)).saveSalle(any(Salle.class));
    }

    @Test
    public void testDeleteSalle() throws Exception {
        doNothing().when(salleService).deleteSalle(1L);
        mockMvc.perform(delete("/api/salles/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(salleService, times(1)).deleteSalle(1L);
    }
}
