package com.example.zenika_meeting_planner.entities;
import com.example.zenika_meeting_planner.enums.TypeReunion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Reunion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TypeReunion type;

    private int nombrePersonnes;

    private LocalTime heureDebut;
    private LocalTime heureFin;
}