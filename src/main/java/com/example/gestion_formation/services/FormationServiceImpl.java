package com.example.gestion_formation.services;

import com.example.gestion_formation.entities.Adherent;
import com.example.gestion_formation.entities.Formation;
import com.example.gestion_formation.exeptions.NotFoundException;
import com.example.gestion_formation.repositories.AdherentRepository;
import com.example.gestion_formation.repositories.FormationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FormationServiceImpl implements FormationService{

    @Autowired
    private FormationRepository formationRepository;

    @Autowired
    private AdherentRepository adherentRepository;

    @Override
    public List<Formation> getAllFormations() {
        return formationRepository.findAll();
    }

    @Override
    public Formation getFormationById(Long id) {
        Optional<Formation> optionalFormation = formationRepository.findById(id);
        if (optionalFormation.isPresent()) {
            return optionalFormation.get();
        } else {
            throw new NotFoundException("Formation not found with id: " + id);
        }
    }


    @Override
    public String deleteFormation(Long id) {
        Optional<Formation> optionalAuthor = formationRepository.findById(id);
        if (optionalAuthor.isPresent()) {
            formationRepository.deleteById(id);
                return "Formation deleted successfully.";
        } else {
            throw new NotFoundException("Formation not found with id: " + id);
        }
    }

    @Override
    public Formation updateFormation(Long id, Formation updatedFormation) {
        Optional<Formation> optionalFormation = formationRepository.findById(id);
        if (optionalFormation.isPresent()) {
            Formation existingFormation = optionalFormation.get();

            existingFormation.setDescription(updatedFormation.getDescription());
            existingFormation.setTitle(updatedFormation.getTitle());
            existingFormation.setEndDate(updatedFormation.getEndDate());
            existingFormation.setStartDate(updatedFormation.getStartDate());

            return formationRepository.save(existingFormation);
        } else {
            throw new NotFoundException("Formation not found with id: " + id);
        }
    }

    @Override
    public Formation addFormation(Formation f) {
        return formationRepository.save(f);
    }



    public List<Formation> getFormationsByTitle(String formationTitle){
        if(formationTitle == null || formationTitle.trim().isEmpty()) {
            return getAllFormations();
        }

        List<Formation> formationList = this.formationRepository.findAll()
                .stream().filter(formation -> formation.getTitle().toLowerCase().contains(formationTitle.toLowerCase()))
                .collect(Collectors.toList());

        return formationList.stream()
                .map(formation -> {
                    Formation mappedFormation = new Formation();

                    mappedFormation.setId(formation.getId());
                    mappedFormation.setTitle(formation.getTitle());
                    mappedFormation.setDescription(formation.getDescription());
                    mappedFormation.setStartDate(formation.getStartDate());
                    mappedFormation.setEndDate(formation.getEndDate());

                    return mappedFormation;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Formation> getFormationsByAdherentId(Long adherentId) {
        List<Formation> allFormations = formationRepository.findAll();
        List<Formation> formationsByAdherentId = new ArrayList<>();

        for (Formation formation : allFormations) {
            for (Adherent adherent : formation.getAdherents()) {
                if (adherent.getId().equals(adherentId)) {
                    formationsByAdherentId.add(formation);
                    break;
                }
            }
        }

        return formationsByAdherentId;
    }

@Override
public void addNewAdherentToFormation(Long formationId, Adherent adherent) {
    Formation formation = formationRepository.findById(formationId)
            .orElseThrow(() -> new NotFoundException("Formation not found"));

    if (formation.getAdherents().contains(adherent)) {
        throw new IllegalArgumentException("Adherent is already associated with the formation");
    }

    adherent.getFormations().add(formation);
    formation.getAdherents().add(adherent);
    adherentRepository.save(adherent);
    formationRepository.save(formation);
}

}
