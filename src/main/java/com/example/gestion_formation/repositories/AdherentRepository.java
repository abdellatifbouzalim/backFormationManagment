package com.example.gestion_formation.repositories;

import com.example.gestion_formation.entities.Adherent;
import com.example.gestion_formation.entities.Formation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AdherentRepository extends JpaRepository<Adherent,Long> {
    List<Adherent> findByFirstNameContainingIgnoreCase(String firstName);
}