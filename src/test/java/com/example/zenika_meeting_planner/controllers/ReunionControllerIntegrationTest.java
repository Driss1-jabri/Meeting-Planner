package com.example.zenika_meeting_planner.controllers;
import com.example.zenika_meeting_planner.DTOs.ReunionDTO;
import com.example.zenika_meeting_planner.entities.Salle;
import com.example.zenika_meeting_planner.entities.Reunion;
import com.example.zenika_meeting_planner.enums.Equipement;
import com.example.zenika_meeting_planner.enums.TypeReunion;
import com.example.zenika_meeting_planner.repositories.ReunionRepository;
import com.example.zenika_meeting_planner.repositories.ReservationRepository;
import com.example.zenika_meeting_planner.repositories.SalleRepository;
import com.example.zenika_meeting_planner.services.ReunionService;
import com.example.zenika_meeting_planner.services.SalleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class ReunionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReunionRepository reunionRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private SalleRepository salleRepository;

    @Autowired
    private SalleService salleService;

    @Autowired
    private ReunionService reunionService;

    @BeforeEach
    public void setUp() {

        salleRepository.deleteAll();
        reservationRepository.deleteAll();
        reunionRepository.deleteAll();


        Salle salle1 = new Salle(null, "Salle A", 10, List.of(Equipement.ECRAN, Equipement.TABLEAU));
        Salle salle2 = new Salle(null, "Salle B", 20, List.of(Equipement.ECRAN, Equipement.TABLEAU, Equipement.WEBCAM));
        salleRepository.save(salle1);
        salleRepository.save(salle2);
    }



    @Test
    public void testGetAllReunions() throws Exception {
        ReunionDTO reunionDTO = new ReunionDTO(null, TypeReunion.VC, 10, LocalTime.of(9, 0), LocalTime.of(10, 0));
        Reunion reunion = new Reunion();
        reunion.setType(reunionDTO.getType());
        reunion.setNombrePersonnes(reunionDTO.getNombrePersonnes());
        reunion.setHeureDebut(reunionDTO.getHeureDebut());
        reunion.setHeureFin(reunionDTO.getHeureFin());
        reunionService.saveReunion(reunion);

        mockMvc.perform(get("/api/reunions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}