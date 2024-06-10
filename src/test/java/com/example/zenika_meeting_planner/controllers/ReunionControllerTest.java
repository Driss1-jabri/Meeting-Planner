package com.example.zenika_meeting_planner.controllers;
import com.example.zenika_meeting_planner.entities.Salle;
import com.example.zenika_meeting_planner.services.ReunionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Arrays;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ReunionControllerTest {

    @Mock
    private ReunionService reunionService;
    @InjectMocks
    private ReunionController reunionController;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(reunionController).build();
    }

    @Test
    public void testGetMeilleureSallePourReunionSuccess() throws Exception {
        Salle salle = new Salle(1L, "E1001", 23, Arrays.asList());
        when(reunionService.obtenirMeilleureSallePourReunion(1L)).thenReturn(Optional.of(salle));
        mockMvc.perform(get("/api/reunions/1/meilleure-salle")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nom").value("E1001"));
        verify(reunionService, times(1)).obtenirMeilleureSallePourReunion(1L);
    }

    @Test
    public void testGetMeilleureSallePourReunionNotFound() throws Exception {
        when(reunionService.obtenirMeilleureSallePourReunion(1L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/reunions/1/meilleure-salle")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(reunionService, times(1)).obtenirMeilleureSallePourReunion(1L);
    }
}
