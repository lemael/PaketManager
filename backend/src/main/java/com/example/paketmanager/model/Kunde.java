package com.example.paketmanager.model;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@Entity
@Table(name = "kunden")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Kunde {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String mainContact;

    private String city;

    private String contact; // ex: +49 30 123456

    private String monthlyVolume;

    @OneToMany(mappedBy = "kunde", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("kunde")
    private List<Coli> colis;

    public void setKundeId(Long kundeId) {
        this.id = kundeId;
    }
    @JsonIgnore
    public Long getKundeId() {
        return this.id;
    }
}