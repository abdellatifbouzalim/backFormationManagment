package com.example.gestion_formation.controllers;

import com.example.gestion_formation.entities.Adherent;
import com.example.gestion_formation.entities.Formation;
import com.example.gestion_formation.responses.ApiResponse;
import com.example.gestion_formation.services.AdherentService;
import com.example.gestion_formation.services.FormationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adherents")
public class AdherentController {

    @Autowired
    private AdherentService adherentService;
    private FormationService formationService;

    @GetMapping
    public ResponseEntity<List<Adherent>> getAllAdherents() {
        List<Adherent> adherents = this.adherentService.getAllAdherents();
        return ResponseEntity.ok(adherents);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Adherent> getAdherentById(@PathVariable Long id) {
        Adherent adherent = this.adherentService.getAdherentById(id);
        return ResponseEntity.ok(adherent);
    }

    @PostMapping
    public ResponseEntity<Adherent> addAdherent(@RequestBody Adherent adherent) {
        Adherent addedAdherent = this.adherentService.addAdherent(adherent);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedAdherent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Adherent> updateAdherent(@PathVariable Long id, @RequestBody Adherent updatedAdherent) {
        Adherent updated = this.adherentService.updateAdherent(id, updatedAdherent);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{formationId}/{adherentId}")
    public ResponseEntity<ApiResponse<String>> removeAdherentFromFormation(@PathVariable Long formationId, @PathVariable Long adherentId) {
        adherentService.removeAdherentFromFormation(formationId, adherentId);
        ApiResponse<String> response = new ApiResponse<>(200, "Adherent deleted successfully from the formation.", null);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/adherents/{adherentName}")
    public List<Adherent> getAdherentsByName(@PathVariable String adherentName) {
        return adherentService.getAdherentsByName(adherentName);
    }

    /* list of adherent for formation */

    @GetMapping("/formations/{formationId}")
    public List<Adherent> getAdherentsFormationById(@PathVariable Long formationId) {
        return adherentService.getAdherentsFormationById(formationId);
    }

}
