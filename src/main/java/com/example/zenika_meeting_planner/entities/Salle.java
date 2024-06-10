package com.example.zenika_meeting_planner.entities;
import com.example.zenika_meeting_planner.enums.Equipement;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor @NoArgsConstructor @Data
public class Salle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private int capacite;
    @ElementCollection(targetClass = Equipement.class)
    @Enumerated(EnumType.STRING)
    private List<Equipement> equipements;
}