package com.example.zenika_meeting_planner.controllers;

import com.example.zenika_meeting_planner.DTOs.SalleDTO;
import com.example.zenika_meeting_planner.entities.Salle;
import com.example.zenika_meeting_planner.enums.Equipement;
import com.example.zenika_meeting_planner.repositories.SalleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class SalleControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SalleRepository salleRepository;

    @Test
    public void testCreateSalle() throws Exception {
        SalleDTO salleDTO = new SalleDTO(null, "E1001", 23, Arrays.asList(Equipement.ECRAN, Equipement.PIEUVRE));
        mockMvc.perform(post("/api/salles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nom\":\"E1001\",\"capacite\":23,\"equipements\":[\"ECRAN\",\"PIEUVRE\"]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("E1001"));
    }

    @Test
    public void testGetAllSalles() throws Exception {
        mockMvc.perform(get("/api/salles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testDeleteSalle() throws Exception {
        Salle salle = salleRepository.save(new Salle(null, "E1001", 23, Arrays.asList(Equipement.ECRAN, Equipement.PIEUVRE)));
        mockMvc.perform(delete("/api/salles/" + salle.getId()))
                .andExpect(status().isNoContent());
    }
}
