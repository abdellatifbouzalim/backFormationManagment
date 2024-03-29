package com.example.gestion_formation.services;

import com.example.gestion_formation.entities.Adherent;
import com.example.gestion_formation.entities.Formation;
import com.example.gestion_formation.exeptions.IllegalArgumentException;
import com.example.gestion_formation.exeptions.NotFoundException;
import com.example.gestion_formation.repositories.AdherentRepository;
import com.example.gestion_formation.repositories.FormationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdherentServiceImpl implements AdherentService {

    @Autowired
    private AdherentRepository adherentRepository;
    @Autowired
     private FormationRepository formationRepository;

    @Override
    public List<Adherent> getAllAdherents() {
        return adherentRepository.findAll();
    }

    @Override
    public Adherent getAdherentById(Long id) {
        Optional<Adherent> optionalAdherent = adherentRepository.findById(id);
        if (optionalAdherent.isPresent()) {
            return optionalAdherent.get();
        } else {
            throw new NotFoundException("Adherent not found with id: " + id);
        }
    }


    @Override
    public Adherent addAdherent(Adherent adherent) {
        return adherentRepository.save(adherent);
    }



    @Override
    public Adherent updateAdherent(Long id, Adherent updatedAdherent) {
        Optional<Adherent> optionalAdherent = adherentRepository.findById(id);
        if (optionalAdherent.isPresent()) {
            Adherent existingAdherent = optionalAdherent.get();
            existingAdherent.setFirstName(updatedAdherent.getFirstName());
            existingAdherent.setLastName(updatedAdherent.getLastName());
            existingAdherent.setFormations(updatedAdherent.getFormations()); // You may need to handle formation updates here
            return adherentRepository.save(existingAdherent);
        } else {
            throw new NotFoundException("Adherent not found with id: " + id);
        }
    }

    @Override
    public void removeAdherentFromFormation(Long formationId, Long adherentId) {
        Formation formation = formationRepository.findById(formationId)
                .orElseThrow(() -> new NotFoundException("Formation not found"));

        Adherent adherent = adherentRepository.findById(adherentId)
                .orElseThrow(() -> new NotFoundException("Adherent not found"));

        // Vérifier si l'adhérent est associé à la formation
        if (!adherent.getFormations().contains(formation)) {
            throw new IllegalArgumentException("Adherent is not associated with the provided formation");
        }

        // Supprimer l'association entre l'adhérent et la formation
        adherent.getFormations().remove(formation);
        formation.getAdherents().remove(adherent);

        adherentRepository.save(adherent);
        formationRepository.save(formation);

        // Supprimer l'adhérent s'il n'est associé à aucune formation
        if (adherent.getFormations().isEmpty()) {
            adherentRepository.delete(adherent);
        }
    }

    @Override
    public List<Adherent> getAdherentsByName(String adherentName) {
        List<Adherent> allAdherents = adherentRepository.findAll(); // Récupérer tous les adhérents
        List<Adherent> adherentsByName = new ArrayList<>();

        for (Adherent adherent : allAdherents) {
            if (adherent.getFirstName().toLowerCase().contains(adherentName.toLowerCase())) {
                adherentsByName.add(adherent);
            }
        }

        return adherentsByName;
    }


    @Override

    public List<Adherent> getAdherentsFormationById(Long formationId) {
        List<Adherent> allAdherents = adherentRepository.findAll();
        List<Adherent> adherentsByFormationId = new ArrayList<>();

        for (Adherent adherent : allAdherents) {
            for (Formation formation : adherent.getFormations()) {
                if (formation.getId() == formationId) {
                    adherentsByFormationId.add(adherent);
                    break;
                }
            }
        }

        return adherentsByFormationId;
    }



}
