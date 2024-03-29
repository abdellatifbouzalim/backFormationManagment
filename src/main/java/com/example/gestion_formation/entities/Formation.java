package com.example.gestion_formation.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Formation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 3, message = "Name should contain at least {min} characters")
    private String title;

    private String description;

    @NotNull(message = "Begin date cannot be empty.")
    @FutureOrPresent(message = "Begin date must be in the future or present.")
    private LocalDate startDate;

    @NotNull(message = "End date cannot be empty.")
    @FutureOrPresent(message = "End date must be in the future or present.")
    private LocalDate endDate;

    @ManyToMany
    @JoinTable(
            name = "formation_adherent",
            joinColumns = @JoinColumn(name = "formation_id"),
            inverseJoinColumns = @JoinColumn(name = "adherent_id")
    )
    @JsonIgnore
    private Set<Adherent> adherents = new HashSet<>();
}
