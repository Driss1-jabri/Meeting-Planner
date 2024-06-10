package com.example.zenika_meeting_planner;

import com.example.zenika_meeting_planner.entities.Reunion;
import com.example.zenika_meeting_planner.entities.Salle;
import com.example.zenika_meeting_planner.enums.Equipement;
import com.example.zenika_meeting_planner.enums.TypeReunion;
import com.example.zenika_meeting_planner.repositories.ReunionRepository;
import com.example.zenika_meeting_planner.repositories.SalleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalTime;
import java.util.Arrays;

@SpringBootApplication
public class ZenikaMeetingPlannerApplication {
    @Autowired
    private ReunionRepository reunionRepository;

    @Autowired
    private SalleRepository salleRepository;

    public static void main(String[] args) {
        SpringApplication.run(ZenikaMeetingPlannerApplication.class, args);
    }
    @PostConstruct
    public void init() {

        salleRepository.save(new Salle(null, "E1001", 23, Arrays.asList()));
        salleRepository.save(new Salle(null, "E1002", 10, Arrays.asList(Equipement.ECRAN)));
        salleRepository.save(new Salle(null, "E1003", 8, Arrays.asList(Equipement.PIEUVRE)));
        salleRepository.save(new Salle(null, "E1004", 4, Arrays.asList(Equipement.TABLEAU)));
        salleRepository.save(new Salle(null, "E2001", 4, Arrays.asList()));
        salleRepository.save(new Salle(null, "E2002", 15, Arrays.asList(Equipement.ECRAN, Equipement.WEBCAM)));
        salleRepository.save(new Salle(null, "E2003", 7, Arrays.asList()));
        salleRepository.save(new Salle(null, "E2004", 9, Arrays.asList(Equipement.TABLEAU)));
        salleRepository.save(new Salle(null, "E3001", 13, Arrays.asList(Equipement.ECRAN, Equipement.WEBCAM, Equipement.PIEUVRE)));
        salleRepository.save(new Salle(null, "E3002", 8, Arrays.asList()));
        salleRepository.save(new Salle(null, "E3003", 9, Arrays.asList(Equipement.ECRAN, Equipement.PIEUVRE)));
        salleRepository.save(new Salle(null, "E3004", 4, Arrays.asList()));

        reunionRepository.save(new Reunion(null, TypeReunion.VC, 8, LocalTime.of(9, 0), LocalTime.of(10, 0)));
        reunionRepository.save(new Reunion(null, TypeReunion.VC, 6, LocalTime.of(9, 0), LocalTime.of(10, 0)));
        reunionRepository.save(new Reunion(null, TypeReunion.RC, 4, LocalTime.of(11, 0), LocalTime.of(12, 0)));
        reunionRepository.save(new Reunion(null, TypeReunion.RS, 2, LocalTime.of(11, 0), LocalTime.of(12, 0)));
        reunionRepository.save(new Reunion(null, TypeReunion.SPEC, 9, LocalTime.of(8, 0), LocalTime.of(9, 0)));
        reunionRepository.save(new Reunion(null, TypeReunion.RC, 7, LocalTime.of(9, 0), LocalTime.of(10, 0)));
        reunionRepository.save(new Reunion(null, TypeReunion.VC, 9, LocalTime.of(8, 0), LocalTime.of(9, 0)));
        reunionRepository.save(new Reunion(null, TypeReunion.SPEC, 10, LocalTime.of(8, 0), LocalTime.of(9, 0)));
        reunionRepository.save(new Reunion(null, TypeReunion.SPEC, 5, LocalTime.of(9, 0), LocalTime.of(10, 0)));
        reunionRepository.save(new Reunion(null, TypeReunion.RS, 4, LocalTime.of(9, 0), LocalTime.of(10, 0)));
        reunionRepository.save(new Reunion(null, TypeReunion.RC, 8, LocalTime.of(9, 0), LocalTime.of(10, 0)));
        reunionRepository.save(new Reunion(null, TypeReunion.VC, 12, LocalTime.of(11, 0), LocalTime.of(12, 0)));
        reunionRepository.save(new Reunion(null, TypeReunion.SPEC, 5, LocalTime.of(11, 0), LocalTime.of(12, 0)));
        reunionRepository.save(new Reunion(null, TypeReunion.VC, 3, LocalTime.of(8, 0), LocalTime.of(9, 0)));
        reunionRepository.save(new Reunion(null, TypeReunion.SPEC, 2, LocalTime.of(8, 0), LocalTime.of(9, 0)));
        reunionRepository.save(new Reunion(null, TypeReunion.VC, 12, LocalTime.of(8, 0), LocalTime.of(9, 0)));
        reunionRepository.save(new Reunion(null, TypeReunion.VC, 6, LocalTime.of(10, 0), LocalTime.of(11, 0)));
        reunionRepository.save(new Reunion(null, TypeReunion.RS, 2, LocalTime.of(11, 0), LocalTime.of(12, 0)));
        reunionRepository.save(new Reunion(null, TypeReunion.RS, 4, LocalTime.of(9, 0), LocalTime.of(10, 0)));
        reunionRepository.save(new Reunion(null, TypeReunion.RC, 7, LocalTime.of(9, 0), LocalTime.of(10, 0)));
    }

}
