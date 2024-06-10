package com.example.zenika_meeting_planner.DTOs;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReservationDTO {
    private Long id;
    private SalleDTO salle;
    private ReunionDTO reunion;
    public ReservationDTO(Long id, SalleDTO salle, ReunionDTO reunion) {
        this.id = id;
        this.salle = salle;
        this.reunion = reunion;
    }
}
