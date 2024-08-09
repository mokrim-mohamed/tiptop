package com.g2.tiptopG2.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CodeGaint")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeGaintEntity implements Serializable {
    @Id
    @Column(length = 10)
    private String id;

    @Column(nullable = false)
    private String titre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private ClientEntity client; // Utilisez ClientEntity ici
}
