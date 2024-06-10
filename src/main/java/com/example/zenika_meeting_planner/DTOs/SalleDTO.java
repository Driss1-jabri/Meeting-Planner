package com.example.zenika_meeting_planner.DTOs;
import com.example.zenika_meeting_planner.entities.Salle;
import com.example.zenika_meeting_planner.enums.Equipement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalleDTO {
    private Long id;
    private String nom;
    private int capacite;
    private List<Equipement> equipements;

    public SalleDTO(Salle salle) {
        this.id = salle.getId();
        this.nom = salle.getNom();
        this.capacite = salle.getCapacite();
    }
}
