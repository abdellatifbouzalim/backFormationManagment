package com.example.gestion_formation.services;

import com.example.gestion_formation.entities.Adherent;
import com.example.gestion_formation.entities.Formation;

import java.util.List;

public interface FormationService {

    public List<Formation> getAllFormations();
    public Formation getFormationById(Long id);
    public String deleteFormation(Long id );
    Formation updateFormation(Long id, Formation f);
    public Formation addFormation(Formation f);
    public List<Formation> getFormationsByTitle(String formationTitle);

    List<Formation> getFormationsByAdherentId(Long adherentId);

    void addNewAdherentToFormation(Long formationId, Adherent adherent);
}
