package com.example.zenika_meeting_planner.services;

import com.example.zenika_meeting_planner.entities.Reunion;
import com.example.zenika_meeting_planner.entities.Salle;

import java.util.List;
import java.util.Optional;

public interface ReunionService {
    public Optional<Salle> obtenirMeilleureSallePourReunion(Long reunionId);
    public void deleteReunion(Long id);
    public Reunion saveReunion(Reunion reunion);
    public List<Reunion> findAllReunions();


}
