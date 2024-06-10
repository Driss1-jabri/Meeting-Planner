package com.example.zenika_meeting_planner.DTOs;
import com.example.zenika_meeting_planner.enums.TypeReunion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReunionDTO {
    private Long id;
    private TypeReunion type;
    private int nombrePersonnes;
    private LocalTime heureDebut;
    private LocalTime heureFin;
}
