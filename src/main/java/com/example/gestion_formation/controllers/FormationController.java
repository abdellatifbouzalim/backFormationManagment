package com.example.gestion_formation.controllers;

import com.example.gestion_formation.entities.Adherent;
import com.example.gestion_formation.entities.Formation;
import com.example.gestion_formation.exeptions.NotFoundException;
import com.example.gestion_formation.responses.ApiResponse;
import com.example.gestion_formation.services.FormationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/formations")
public class FormationController {

    @Autowired
    private FormationService formationService;

    @GetMapping
    public ResponseEntity<List<Formation>> getAllFormations(){
        List<Formation> formations = this.formationService.getAllFormations();
        return ResponseEntity.ok(formations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Formation> getFormationById(@PathVariable Long id){
        Formation formation = this.formationService.getFormationById(id);
        return ResponseEntity.ok(formation);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> addFormation(@Valid @RequestBody Formation formation){
        this.formationService.addFormation(formation);
        ApiResponse<Void> response = new ApiResponse<>(HttpStatus.CREATED.value(), "Formation added successfully", null);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> updateFormation(@PathVariable Long id, @Valid @RequestBody Formation updatedFormation){
        this.formationService.updateFormation(id, updatedFormation);
        ApiResponse<Void> response = new ApiResponse<>(HttpStatus.OK.value(), "Formation updated successfully", null);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteFormation(@PathVariable Long id){
        this.formationService.deleteFormation(id);
        ApiResponse<Void> response = new ApiResponse<>(HttpStatus.NO_CONTENT.value(), "Formation deleted successfully", null);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search/{title}")
    public ResponseEntity<List<Formation>> searchFormationsByTitle(@PathVariable String title) {
        List<Formation> formations = formationService.getFormationsByTitle(title);
        return ResponseEntity.ok(formations);
    }

    /* list of formations for adherents */
    @GetMapping("/adherents/{adherentId}")
    public List<Formation> getFormationsAdherent(@PathVariable Long adherentId) {
        return formationService.getFormationsByAdherentId(adherentId);
    }

    @PostMapping("/{formationId}/adherents")
    public ResponseEntity<ApiResponse<Void>> addNewAdherentToFormation(@PathVariable Long formationId, @RequestBody Adherent adherent) {
        try {
            formationService.addNewAdherentToFormation(formationId, adherent);
            ApiResponse<Void> response = new ApiResponse<>(HttpStatus.CREATED.value(), "Adherent added to formation successfully.", null);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (NotFoundException e) {
            ApiResponse<Void> response = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Formation not found.", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (IllegalArgumentException e) {
            ApiResponse<Void> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


}
