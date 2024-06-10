package com.example.zenika_meeting_planner.services;

import com.example.zenika_meeting_planner.entities.Reunion;
import com.example.zenika_meeting_planner.entities.Salle;
import com.example.zenika_meeting_planner.enums.TypeReunion;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface SalleService {
    Optional<Salle> trouverMeilleureSalle(Reunion reunion);

    public List<Salle> filterByCapacity(List<Salle> salles, int nombrePersonnes);
    public List<Salle> filterByEquipments(List<Salle> salles, TypeReunion typeReunion);
    public List<Salle> sortBySuitability(List<Salle> salles, Reunion reunion);
    public List<Salle> filterByAvailability(List<Salle> salles, Reunion reunion);
    public Salle saveSalle(Salle salle);
    public void deleteSalle(Long id);
    public List<Salle> findAllSalles();
    public List<Salle> findAllAvailableSalles(LocalTime heureDebut, LocalTime heureFin);
    public boolean isAvailable(Salle salle, LocalTime heureDebut, LocalTime heureFin);
    public Optional<Salle> findById(Long salleId);
}
