package com.g2.tiptopG2.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
    private String nom;
    private String prenom;
    private String sexe;
    private String email;
    private String telephone;
    private String motDePasse;
    private Integer roleId;  
}