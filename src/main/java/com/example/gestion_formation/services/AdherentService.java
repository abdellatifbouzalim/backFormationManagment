package com.example.gestion_formation.services;

import com.example.gestion_formation.entities.Adherent;

import java.util.List;

public interface AdherentService {
    public List<Adherent> getAllAdherents();
    public Adherent getAdherentById(Long id);

    Adherent updateAdherent(Long id, Adherent a);
    public Adherent addAdherent(Adherent a);

    void removeAdherentFromFormation(Long formationId, Long adherentId);

    public List<Adherent> getAdherentsByName(String adherentName);

    public List<Adherent> getAdherentsFormationById(Long formationId);

}
